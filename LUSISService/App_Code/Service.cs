using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

// NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service" in code, svc and config file together.
public class Service : IService
{
    readonly MainController _main = new MainController();

    public LoginDetails login(LoginDetails loginCred)
    {
        var emp = _main.checkLogin(loginCred.EmpId, loginCred.Password);

        if (emp == null)
        {
            return null;
        }
        const bool status = true;
        return new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, status, emp.DeptCode,
            emp.Department.Name, emp.AdditionalRole);
    }

    //Retrieval list
    public Retrieval_List viewRetrievalList()
    {
        var retrievalItems = _main.receiveRetrievalByItem();

        var tmpList = new List<RetrievalList>();
        foreach (var retByItem in retrievalItems)
        {
            var disbursement = new Dictionary<string, DisbursementList>();
            var tempList = new List<DeptBreakDown>();
            foreach (var dispItem in retByItem.DisbursementItems)
            {
                tempList.Add(new DeptBreakDown(dispItem.DeptCode, dispItem.Department.Name, dispItem.RequestedQty,
                    dispItem.RetrievedQuantity, dispItem.RetrievalID));
                disbursement.Add(dispItem.DeptCode,
                    new DisbursementList(dispItem.Department.CollectionPoint, dispItem.Department.CollectionPoint1.Location, DateTime.Now.ToString("dd-MMM-yyyy"), null,
                        dispItem.Department.Employees.First(i => i.AdditionalRole == "Representative").EmpID, "Ready", null));
            }
            tmpList.Add(new RetrievalList(retByItem.RetrievedQty, retByItem.Item.BinId, retByItem.ItemCode,
                retByItem.Item.Description, retByItem.Item.UnitOfMeasure, retByItem.RequestedQty,
                retByItem.Item.CurrentQuantity, tempList, disbursement));
        }
        return new Retrieval_List(false, tmpList);
    }

    //Update retrieved items
    public Boolean updateRetrievalList(Retrieval_List retrievalList)
    {

        var disbList = new List<DisbursementItem>();
        var disbursement = new Dictionary<string, LUSIS_EF_FACADE.DisbursementList>();

        foreach (var retList in retrievalList.Data)
        {
            disbList.AddRange(retList.ItemByDept.Select(item => new DisbursementItem
            {
                DeptCode = item.DeptCode, RetrievalID = item.RetrievalId, RetrievedQuantity = Convert.ToInt32(item.ItemRetrieved), ItemCode = retList.ItemCode
            }));


            foreach (var disb in retList.DisbursementLists)
            {
                if (!disbursement.ContainsKey(disb.Key))
                    disbursement.Add(disb.Key,
                        new LUSIS_EF_FACADE.DisbursementList
                        {
                            CollectionPoint = disb.Value.CPoint,
                            DisbursementDate = Convert.ToDateTime(disb.Value.DisbDate),
                            RepresentativeID = disb.Value.EmpId,
                            Status = disb.Value.Status
                        });
            }
        }
        return _main.generateDisb(disbList, disbursement, retrievalList.ResponseStatus ? "finalize" : "");
    }

    //Search Item
    public SearchDetails searchItems(String key)
    {
        var item = _main.getSearchItemCatalog(key);
        if (item == null)
        {
            return null;
        }
        var tmpList = item.Select(i => new Items(i.UnitOfMeasure, i.ItemCode, i.Description)).ToList();
        return new SearchDetails(true, tmpList);
    }

    //Requisition list by dept
    public List<Requisition_Details> getDeptRequisitionList(String deptCode)
    {
        var reqList = _main.getDeptAppRequisitionList(deptCode);
        return (from req in reqList
                select _main.GetReqDetailsByReqId(req.RequisitionID)
                    into reqDet
                    let tmpList =
                        reqDet.Select(
                            reqdet =>
                                new RequisitionDetails(reqdet.Item.UnitOfMeasure, reqdet.Quantity, reqdet.Item.Category,
                                    reqdet.ItemCode, reqdet.Item.Description)).ToList()
                    let emp = _main.SelectEmployeeById(reqDet[0].Requisition.EmpID)
                    let loginDetails =
                        new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, true, emp.DeptCode, emp.Department.Name,
                            emp.AdditionalRole)
                    select
                        new Requisition_Details(tmpList, loginDetails, reqDet[0].RequisitionID,
                            reqDet[0].Requisition.RequestedDate != null ? reqDet[0].Requisition.RequestedDate.Value.ToString("dd-MMM-yyyy") : null, reqDet[0].Requisition.RequisitionComment,
                            reqDet[0].Requisition.ApprovalStatus, reqDet[0].Requisition.RejectionComment)).ToList();
    }

    public bool updateRequisitionStatus(Requisition_Details requisitionDetails)
    {
        var req = new Requisition
        {
            ApprovalStatus = requisitionDetails.Status,
            RejectionComment = requisitionDetails.RejComment,
            RequisitionID = requisitionDetails.ReqId
        };
        return (_main.approveNotification(req));
    }

    //Get Dept Details
    public DeptDetails getDeptDetails(String deptCode)
    {

        var cpt = _main.getAllCPointsLocation();
        var cPoint = cpt.Select(cp => new CollectionPoints(cp.Location, cp.Id, cp.Desciption)).ToList();

        var location = _main.getDeptCPointLocation(deptCode).Location;
        var emp = _main.getDeptRepName(deptCode);
        var prevRep = new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, true, emp.DeptCode,
            emp.Department.Name, emp.AdditionalRole);
        var empList = _main.getDeptEmployees(deptCode, "Representative").ToList();
        var employees =
            empList.Select(
                e =>
                    new LoginDetails(e.EmpID, e.Password, e.Name, e.Role, true, e.DeptCode, e.Department.Name,
                        e.AdditionalRole)).ToList();
        return new DeptDetails(location, prevRep, employees, cPoint);
    }

    //Update Dept details
    public Boolean updateDeptDetails(DeptDetails collPoint)
    {
        return _main.updateDeptDetails(collPoint.PrevRep.EmployeeName, collPoint.NewRep.EmployeeName, collPoint.Location,
            collPoint.PrevRep.DeptCode);
    }

    public Delegate getDelegate(string deptCode)
    {
        var delDelegate = _main.getDelegate(deptCode);
        var emp = _main.getDeptEmployees(deptCode, "Delegate");
        var empList =
            emp.Select(
                e =>
                    new LoginDetails(e.EmpID, e.Password, e.Name, e.Role, true, e.DeptCode, e.Department.Name,
                        e.AdditionalRole)).ToList();
        if (delDelegate == null)
        {
            return new Delegate(null, null, null, empList);
        }
        return
            new Delegate(
                new LoginDetails(delDelegate.Employee.EmpID, delDelegate.Employee.Password, delDelegate.Employee.Name,
                    delDelegate.Employee.Role, true, delDelegate.Employee.DeptCode, delDelegate.Employee.Department.Name,
                    delDelegate.Employee.AdditionalRole),
                (delDelegate.StartDate != null ? delDelegate.StartDate.Value.ToString("dd-MMM-yyyy") : null),
                (delDelegate.EndDate != null ? delDelegate.EndDate.Value.ToString("dd-MMM-yyyy") : null), empList);

    }

    public bool updateDelegate(Delegate delegat)
    {
        if (delegat.LoginDetails.EmpId != null)
        {
            return _main.updateDelegate(delegat.LoginDetails.EmployeeName, delegat.LoginDetails.DeptCode,
                delegat.StartDate,
                delegat.EndDate);
        }
        return _main.clearDelegate(delegat.LoginDetails.DeptCode);
    }

    //View Requisition 
    public Requisition_List viewRequisition(LoginDetails loginCred)
    {
        var emp = _main.SelectEmployeeById(loginCred.EmpId);
        if (emp == null)
        {
            return null;
        }
        var loginDetails = new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, true, emp.DeptCode,
            emp.Department.Name, emp.AdditionalRole);
        var reqList = _main.getEmpRequisitionList(loginCred.EmpId);
        var tmpList =
            reqList.Select(
            req => req.RequestedDate != null ? new RequisitionList(req.RequestedDate.Value.ToString("dd-MMM-yyyy"), req.RequisitionID) : null).ToList();
        return new Requisition_List(tmpList, loginDetails);
    }

    //Requisition Details
    public Requisition_Details viewRequisitionDetails(String id)
    {
        var reqDet = _main.GetReqDetailsByReqId(id);
        var tmpList =
            reqDet.Select(
                reqdet =>
                    new RequisitionDetails(reqdet.Item.UnitOfMeasure, reqdet.Quantity, reqdet.Item.Category,
                        reqdet.ItemCode, reqdet.Item.Description)).ToList();
        var emp = _main.SelectEmployeeById(reqDet[0].Requisition.EmpID);
        var loginDetails = new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, true, emp.DeptCode,
            emp.Department.Name, emp.AdditionalRole);
        var requestedDate = reqDet[0].Requisition.RequestedDate;
        if (requestedDate != null)
            return new Requisition_Details(tmpList, loginDetails, reqDet[0].RequisitionID,
                requestedDate.Value.ToString("dd-MMM-yyyy"), reqDet[0].Requisition.RequisitionComment,
                reqDet[0].Requisition.ApprovalStatus,
                reqDet[0].Requisition.RejectionComment);
        return null;
    }

    //New Requisition
    public Boolean createNewRequisition(Requisition_Details reqDet)
    {
        var emp = _main.SelectEmployeeById(reqDet.Emp.EmpId);
        if (emp == null)
        {
            return false;
        }
        var reqList =
            reqDet.Data.Select(reqdet => new RequisitionDetail { ItemCode = reqdet.ItemCode, Quantity = reqdet.Quantity })
                .ToList();
        return _main.createNewRequisitionWithDetails(emp, reqDet.ReqId, reqDet.Comment, reqList);
    }

    //Generate Retrieval List
    public void generateRetrievalList()
    {
        _main.generateRetrieval();
    }

    //Employee list of specific department
    public List<LoginDetails> employeeList(string deptCode, String addRole)
    {
        var empList = _main.getDeptEmployees(deptCode, addRole);
        return
            empList.Select(
                emp =>
                    new LoginDetails(emp.EmpID, emp.Password, emp.Name, emp.Role, true, emp.DeptCode,
                        emp.Department.Name, emp.AdditionalRole)).ToList();
    }

    public List<DisbursementList> disbursementList()
    {
        var disblist = _main.getDeptDisbList();

        return (from list in disblist
                where list.Status == "Ready"
                let disbdetails = _main.getDeptDisbItemList(list.Id)
                let disp =
                    disbdetails.Select(
                        disburse =>
                            new DisbursementDetails(disburse.RetrievalByItem.Item.Category, disburse.ItemCode, disburse.DisbursementID != null ? (int)disburse.DisbursementID : 0, disburse.RetrievalByItem.Item.Description,
                                disburse.RetrievalByItem.Item.UnitOfMeasure, disburse.RetrievedQuantity != null ? (int)disburse.RetrievedQuantity : 0,
                                disburse.RequestedQty != null ? (int)disburse.RequestedQty : 0, disburse.Status)).ToList()
                select
                    new DisbursementList(list.CollectionPoint, list.CollectionPoint1.Location,
                        list.DisbursementDate != null ? list.DisbursementDate.Value.ToString("dd-MMM-yyyy") : null,
                        list.Employee.Department.Name, list.RepresentativeID, list.Status, disp)).ToList();
    }

    public bool approveDisbursement(AppDisbursement appDisbursement)
    {
        var disp = new List<DisbursementItem>();
        if (appDisbursement.DisbDetail != null)
            disp.AddRange(appDisbursement.DisbDetail.DisbDetails.Select(appdisb => new DisbursementItem
            {
                RequestedQty = appdisb.RequestedQuantity, RetrievedQuantity = appdisb.RetrievedQuantity, Status = appdisb.Status, ItemCode = appdisb.ItemCode, DisbursementID = appdisb.DesbId
            }));


        var log = login(new LoginDetails(appDisbursement.Login.EmpId, appDisbursement.Login.Password));
        return log != null && _main.approveDisbursement(disp, log.DeptCode);
    }

    public List<DiscrepancyList> discrepancyList(String empId)
    {
        var log = _main.getEmployee(empId);

        var list = log.Role == "Clerk" ? _main.getDiscrepancyVoucherList() : _main.getAppDisVoucherList(empId);
        var discrepancyList = new List<DiscrepancyList>();
        foreach (var voucher in list)
        {
            string status;
            var pendList = _main.getPendDisVoucherItems(voucher.VoucherID);
            if (pendList == null)
            {
                status = "Closed";
            }
            else
            {
                status = !pendList.Any() ? "Closed" : "Pending";
            }
            if (log.Role == "Clerk")
            {
                discrepancyList.Add(new DiscrepancyList(voucher.VoucherID,
                    voucher.Date != null ? voucher.Date.Value.ToString("dd-MMM-yyyy") : null,
                    _main.getEmployee(voucher.ApprovedBy).Name, status));
            }
            else
            {
                discrepancyList.Add(new DiscrepancyList(voucher.VoucherID,
                    voucher.Date != null ? voucher.Date.Value.ToString("dd-MMM-yyyy") : null,
                    voucher.Employee.Name, status));
            }
        }
        return discrepancyList;
    }

    public List<DiscrepancyDetails> discrepancyDetails(String voucher, String empId)
    {
        var log = _main.getEmployee(empId);
        var list = log.Role == "Clerk"
            ? _main.getDiscrepancyVoucherItems(voucher)
            : _main.getPendDisVoucherItems(voucher);
        return
            list.Select(
                li => new DiscrepancyDetails(li.DiscrepancyID, li.ItemCode, li.Item.Description, li.Quantity, li.Reason, li.Status))
                .ToList();
    }

    public bool discrepancyApprove(List<DiscrepancyDetails> discrepancyDetails)
    {
        var list = discrepancyDetails.Select(discrepancyDetail => new Discrepancy
        {
            DiscrepancyID = discrepancyDetail.DiscrepancyId,
            Status = discrepancyDetail.Status
        }).ToList();

        return _main.setDiscrepancyList(list);
    }

    public bool raiseDiscrepancy(RaiseDiscrepancy discrepancy)
    {
        var list = discrepancy.Details.Select(discrepance => new Discrepancy
        {
            ItemCode = discrepance.Item,
            Reason = discrepance.Reason,
            Quantity = discrepance.Qty != null ? (int)discrepance.Qty : 0,
            EmpID = discrepancy.EmpId
        }).ToList();
        return _main.addDiscrepancy(list) > 0;
    }

    public List<PurchaseOrder> purchaseOrder()
    {
        var list = _main.getPOdetails();
        return
            list.Select(
                li =>
                    new PurchaseOrder(li.PurchaseOrderNum,
                        li.ExpectedDeliveryDate != null ? li.ExpectedDeliveryDate.Value.ToString("dd-MMM-yyyy") : null,
                        li.Status)).ToList();
    }

    public List<PurchaseOrderDetails> purchaseDetails(int poId)
    {
        var list = _main.GetPoDetails(poId.ToString(CultureInfo.InvariantCulture));
        var pList = _main.GetPoList(poId.ToString(CultureInfo.InvariantCulture));
        var supplier = _main.getSupName(pList.SupplierID);

        return
            list.Select(
                detail =>
                    new PurchaseOrderDetails(supplier,
                        pList.OrderedDate != null ? pList.OrderedDate.Value.ToString("dd-MMM-yyyy") : null,
                        detail.ItemCode, detail.Item.Description,
                        detail.Item.ReOrderQuantity != null ? (int) detail.Item.ReOrderQuantity : 0,
                        detail.Quantity != null ? (int) detail.Quantity : 0)).ToList();

    }

    public bool approvePurchase(int poId)
    {
        return _main.UpdatePoDetail(poId);
    }
}

