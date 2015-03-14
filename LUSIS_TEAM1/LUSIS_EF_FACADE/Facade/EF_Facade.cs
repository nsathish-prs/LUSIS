using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;


namespace LUSIS_EF_FACADE.Facade
{
    public class EF_Facade
    {
        private LUSIS_DBFacade context;


        public EF_Facade()
        {
            context = new LUSIS_DBFacade();
        }

        public List<Item> getdistItemList()
        {
            return (from x in context.Items select x).ToList();
        }
        public List<string> getdeptList()
        {
            return (from x in context.Departments select x.Name).ToList();
        }
        public List<string> getItemsbyDesc(string iCate)
        {
            return (from x in context.Items where x.Category == iCate select x.Description).ToList();
        }

        public List<ItemSupplierDetail> lSupplierDetails(string iCode)
        {
            var z = from x in context.ItemSupplierDetails
                where x.ItemCode == iCode
                select x;
            return z.ToList();
        }

        public string getSupplierID(string supName)
        {
            string sName = (from x in context.Suppliers
                where x.Name == supName
                select x.SupplierID).First();
            return sName;
        }


        public void addpurchaseOrders(PurchaseOrder po)
        {
            context.PurchaseOrders.AddObject(po);
            context.SaveChanges();
            //return noOfOrders;
        }

        public void addPODetail(PurchaseOrderDetail pod)
        {
            context.PurchaseOrderDetails.AddObject(pod);
            context.SaveChanges();
        }

        public int getLastRecord()
        {
            int lRecord = (from x in context.PurchaseOrders
                orderby x.PurchaseOrderNum descending
                select x.PurchaseOrderNum).First();
            return lRecord;
        }

        public List<Item> getItems()
        {
            var i = from x in context.Items
                where x.CurrentQuantity < x.ReOrderLevel && x.Status != "Ordered"
                select x;
            return i.ToList();
        }

        public List<PurchaseOrder> getPOlist()
        {
            var po = from x in context.PurchaseOrders
                     orderby x.OrderedDate descending
                select x;
            return po.ToList();
        }

        public List<PurchaseOrderDetail> getPODetails(int pid)
        {
            List<PurchaseOrderDetail> list = (from x in context.PurchaseOrderDetails
                where x.PurchaseOrderNum == pid
                select x).ToList();

            return list;
        }

        public PurchaseOrder getPOList(int pod)
        {
            PurchaseOrder list = (from x in context.PurchaseOrders
                where x.PurchaseOrderNum == pod
                select x).First();
            return list;
        }

        public string getSupName(string sID)
        {
            Supplier sup = (from x in context.Suppliers
                where x.SupplierID == sID
                select x).First();
            return sup.Name;
        }

        public bool updatePODetail(int pod)
        {
            PurchaseOrder list = (from x in context.PurchaseOrders
                where x.PurchaseOrderNum == pod
                select x).First();
            list.Status = "Approved";
            return context.SaveChanges()>0;
        }

        public string getItemDesc(string iCode)
        {
            Item i = (from x in context.Items
                where x.ItemCode == iCode
                select x).First();
            return i.Description;
        }

        public int getItemMinOrder(string iCode)
        {
            Item i = (from x in context.Items
                where x.ItemCode == iCode
                select x).First();
            return Convert.ToInt32(i.ReOrderQuantity);
        }

        public List<Item> purchaseItems()
        {
            //var pItems = from x in context.Items
            //             join y in context.ItemSupplierDetails
            //             on x.ItemCode equals y.ItemCode
            //             where x.CurrentQuantity < x.ReOrderLevel
            //             select new { x.ItemCode, x.Description, x.ReOrderQuantity, y.UnitPrice };

            var v = from x in context.Items
                where x.CurrentQuantity < x.ReOrderLevel && x.Status != "Ordered"
                select x;
            return v.ToList();
            //return pItems.ToList();

        }

        public void updateItem(Item item, String iCode)
        {
            var x = (from items in context.Items
                where items.ItemCode == iCode
                select items).First();
            x.Status = "Ordered";
            context.SaveChanges();
        }

        public Employee getEmployee(String empId)
        {
            var emp = from x in context.Employees
                where x.EmpID == empId
                select x;

            return emp.FirstOrDefault();
        }
        public bool updatePassword(string uName, string password)
        {
            Employee emp = getEmployee(uName);
            emp.Password = password;
           
            if (context.SaveChanges() != 0)
                return true;
            else
                return false;
        }

        // DeptController (Return List<CollectionPoint> Or Null) // Benny
        public List<CollectionPoint> getAllCollectionPoints()
        {
            var q = from x in context.CollectionPoints
                select x;

            return q.ToList();
        }

        // DeptController (Return CollectionPoint Or Null) // Benny
        public CollectionPoint getDeptCollectionPoint(String deptCode)
        {
            var q = from x in context.Departments
                where x.DeptCode == deptCode
                select x;

            Department dept = q.FirstOrDefault();

            return dept.CollectionPoint1;
        }

        // DeptController (Return List<Employee> For "Delegate" or "Representative" Or Null) // Benny
        public List<Employee> getDeptEmployees(String deptCode, String addRole)
        {
            var q = from x in context.Employees
                where
                    x.DeptCode == deptCode && x.Role == "Employee" &&
                    (x.AdditionalRole == null || x.AdditionalRole == addRole)
                orderby x.Name
                select x;

            return q.ToList();
        }

        // DeptController Return(Employee Or Null) // Benny
        public Employee getDeptRepEmp(String deptCode)
        {
            var q = from x in context.Employees
                where x.DeptCode == deptCode && x.AdditionalRole == "Representative"
                select x;

            return q.FirstOrDefault();
        }

        // Search Employee by Name and Dept Code // Sathish
        public Employee getEmployeeByNameAndDept(String empName, String deptCode)
        {
            var e = from x in context.Employees
                where x.Name == empName && x.DeptCode == deptCode
                select x;

            return e.FirstOrDefault();
        }

        // DeptController Take 4 String, Batch Processing, (Return bool = True Or False) // Benny
        public bool UpdateDeptDetails(String prevRep, String newRep, String newCPoint, String deptCode)
        {
            Employee e1 = getEmployeeByNameAndDept(prevRep, deptCode);

            Employee e2 = getEmployeeByNameAndDept(newRep, deptCode);

            var q3 = from x in context.CollectionPoints
                where x.Location == newCPoint
                select x;

            CollectionPoint c = q3.FirstOrDefault();

            if (e1 != null)
            {
                e1.AdditionalRole = null;
                e1.Department.CollectionPoint = c.Id;
            }
            if (e2 != null)
            {
                e2.AdditionalRole = "Representative";
                DisbursementList dispRecord = (from x in context.DisbursementLists
                    where x.RepresentativeID == e1.EmpID && x.Status == "Ready"
                    select x).FirstOrDefault();
                if (dispRecord != null)
                    dispRecord.RepresentativeID = e2.EmpID;
            }
            context.SaveChanges();
            return true;
        }

        // DeptController // (Return Employee = DelegateEmp) Or Null // Benny
        public Employee getDelegateEmp(String deptCode)
        {
            var q = from x in context.Employees
                where x.DeptCode == deptCode && x.AdditionalRole == "Delegate"
                select x;

            return q.FirstOrDefault();
        }

        // DeptController // Get Delegate By String (deptCode) (Return Delegate Or Null) // Benny
        public LUSIS_EF_FACADE.Delegate getDelegate(String deptCode)
        {
            var q = from x in context.Delegates
                where x.Employee.DeptCode == deptCode
                select x;

            return q.FirstOrDefault();
        }

        // DeptController // Clear Delegate By String (deptCode) // Benny
        public Boolean clearDelegate(String deptCode)
        {
            var q1 = from x in context.Delegates where x.Employee.DeptCode == deptCode select x;
            Delegate del = q1.FirstOrDefault();

            if (del != null)
            {
                Employee e = getEmployee(del.DelegateID);
                if (e.AdditionalRole == "Delegate")
                    e.AdditionalRole = null;

                context.DeleteObject(del);

                return (context.SaveChanges() > 0);
            }
            return false;
        }

        // DeptController // Update (Insert) Delegate By Delegate // Benny
        public Boolean updateDelegate(Delegate del)
        {
            if (del != null)
            {
                context.AddToDelegates(del);
                Employee e = getEmployee(del.DelegateID);
                e.AdditionalRole = "Delegate";
                return (context.SaveChanges() > 0);
            }
            return false;
        }

        public List<Item> getItemCatalog()
        {
            var items = from i in context.Items
                        where i.Status != "InActive"
                        select i;

            if (items != null)
            {
                return items.ToList();
            }
            else
                return null;
        }

        //Search by Item Description
        public List<Item> getSearchItemCatalog(String key)
        {
            var items = from i in context.Items
                where i.Description.Contains(key)
                select i;

            if (items != null)
            {
                return items.ToList();
            }
            else
                return null;
        }


        // To retrieve item from Item table by passing item description.
        public Item getItemByDesc(String description)
        {
            var item = from i in context.Items
                where i.Description == description
                select i;
            return item.FirstOrDefault();
        }

        public string getItemByDescr(string description)
        {
            return ((from i in context.Items
                       where i.Description == description
                       select i.ItemCode).First()).ToString();
        }

        public int insertNewRequisition(Requisition req)
        {
            context.Requisitions.AddObject(req);
            int rowsAffected = context.SaveChanges();
            return rowsAffected;
        }


        // Insert RequisitionDetail
        public int insertRequisitionDetail(RequisitionDetail reqDetail)
        {
            context.RequisitionDetails.AddObject(reqDetail);
            int rowsAffected = context.SaveChanges();
            return rowsAffected;
        }


        // Delete RequisitionDetail
        public int deleteRequisitionDetail(RequisitionDetail reqDetail)
        {
            context.RequisitionDetails.DeleteObject(reqDetail);
            int rowsAffected = context.SaveChanges();
            return rowsAffected;
        }

        // Delete RequisitionDetailsByRequisitionID
        public int deleteReqDetailsByReqID(String reqID)
        {
            int rowsAffected = 0;
            List<RequisitionDetail> reqDetails = getReqDetailsByReqID(reqID);
            foreach (RequisitionDetail reqDetail in reqDetails)
                rowsAffected += deleteRequisitionDetail(reqDetail);

            return rowsAffected;
        }


        // Get DeptRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptRequisitionList(String dept)
        {
            var requisitions = from x in context.Requisitions
                where x.Employee.Department.DeptCode == dept && x.Status == "New"
                orderby x.RequestedDate descending
                select x;

            if (requisitions != null)
            {
                return requisitions.ToList();
            }
            else
                return null;
        }

        // Get EmpRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getEmpRequisitionList(String emp)
        {
            var requisitions = from x in context.Requisitions
                where x.EmpID == emp && x.Status == "New"
                orderby x.RequestedDate descending
                select x;

            if (requisitions != null)
            {
                return requisitions.ToList();
            }
            else
                return null;
        }

        // Get DeptAppRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptAppRequisitionList(String dept)
        {
            var requisitions = from x in context.Requisitions
                where x.Employee.Department.DeptCode == dept && x.Status == "New" && x.ApprovalStatus == "Pending"
                orderby x.RequestedDate ascending
                select x;

            if (requisitions != null)
            {
                return requisitions.ToList();
            }
            else
                return null;
        }

        // Get RequisitionByID (Return Requisition or Null)
        public Requisition getRequisitionByID(String reqID)
        {
            var requisition = from x in context.Requisitions
                where x.RequisitionID == reqID && x.Status == "New"
                select x;

            if (requisition != null)
            {
                return requisition.FirstOrDefault();
            }
            else
                return null;
        }

        // Set Requisition (Return Requisition or Null)
        public Requisition setRequisition(Requisition newReq)
        {
            Requisition req = getRequisitionByID(newReq.RequisitionID);
            req.AuthorizedBy = getDelegateEmp(req.Employee.DeptCode) != null ? getDelegateEmp(req.Employee.DeptCode).EmpID : getDeptHead(req.Employee.DeptCode).EmpID;

            req.RequisitionComment = newReq.RequisitionComment;
            req.RejectionComment = newReq.RejectionComment;
            req.ApprovalStatus = newReq.ApprovalStatus;
            req.Status = newReq.Status;
            context.SaveChanges();

            if (req != null)
            {
                return req;
            }
            else
                return null;
        }

        // Get RequisitionDetailsByRequisitionID (Return List<RequisitionDetail> or Null)
        public List<RequisitionDetail> getReqDetailsByReqID(String reqID)
        {
            var reqDetails = from x in context.RequisitionDetails
                where x.RequisitionID == reqID
                select x;

            if (reqDetails != null)
            {
                return reqDetails.ToList();
            }
            else
                return null;
        }

        // List of Dept ID
        public List<String> getDeptRequisitionId(String deptCode)
        {
            var requisition = from r in context.Requisitions
                where r.Employee.DeptCode == deptCode
                select r.RequisitionID;
            if (requisition != null)
            {
                return requisition.ToList();
            }
            return null;

        }

        // Retrieval list where status is Pending.
        public RetrievalByItem getPendingRetrievalList()
        {
            RetrievalByItem retrieval = (from r in context.RetrievalByItems
                where r.Status == "Pending"
                select r).FirstOrDefault();
            return retrieval;
        }

        // Retrieval list where status is Finalized.
        public void getFinalizedRetrievalList()
        {
            var v = from r in context.RetrievalByItems
                where r.Status == "Finalize"
                select r;
        }

        public int insertRetrievalList(RetrievalByItem ret)
        {
            return 0;
        }

        public int updateRetrievalListStatus(String status)
        {
            return 0;
        }

        public void getRetrievalList()
        {

        }

        // Get Dept DisbursementList -- SUCHITA
        public List<DisbursementList> getDeptDisbList()
        {
            var q1 = from d in context.DisbursementLists
                     orderby d.Id descending
                      select d;

            var list = q1.ToList();
            return list;
        }

        public List<DisbursementList> getDeptDisbList(String deptCode)
        {
            var q1 = from d in context.DisbursementLists
                     where d.Employee.DeptCode == deptCode
                     select d;

            var list = q1.ToList();
            return list;
        }

        // Get Dept Disbursement Item List -- SUCHITA
        public List<DisbursementItem> getDeptDisbItemList(int disbursementId)
        {
            var q2 = from d in context.DisbursementItems
                     where d.DisbursementID == (disbursementId)
                     orderby d.DisbursementID descending
                    select d;

            return q2.ToList();

        }

        public DisbursementItem getDeptDisbItem(int disbursementId, String itemCode)
        {
            DisbursementItem q2 = (from d in context.DisbursementItems
                     where d.DisbursementID == (disbursementId) && d.ItemCode == itemCode
                     select d).FirstOrDefault();

            return q2;

        }

        // receive reatrieval list by Item -- SUCHITA
        public List<RetrievalByItem> receiveRetrievalByItem()
        {
            var q3 = from r in context.RetrievalByItems
                where r.Status == "pending"
                orderby r.ItemCode
                select r;

            return q3.ToList();
        }

        //Cancel disbursement --- SUCHITA
        public void CancelDisbursement(int selectedDisbID)
        {
            var CancelDisbursement = (from x in context.DisbursementLists
                                      where x.Id == selectedDisbID
                                      select x).FirstOrDefault();
            CancelDisbursement.Status = "Cancelled";

            var q6 = from d in context.DisbursementItems
                     where d.DisbursementID == selectedDisbID
                     select d;

            foreach (var v in q6.ToList())
            {
                v.Status = "Cancelled";
        }

            context.SaveChanges();
        }

        // generate Disbursement list by Item -- SUCHITA
             // Generate disbursement list by store clark -- SUCHITA
        public Boolean generateDisb(List<DisbursementItem> disbursementItems, Dictionary<String, DisbursementList> deptDisbLists, String status)
        {
            List<RetrievalByItem> RetrievalByItemList = receiveRetrievalByItem();
            if (RetrievalByItemList.Count == 0)
                    return false;

            List<String> deptsInvolved = new List<String>();

            if (status.Equals("finalize"))
            {
            foreach (String deptCode in deptDisbLists.Keys)
            {
                DisbursementList disbList1 = deptDisbLists[deptCode];

                context.DisbursementLists.AddObject(disbList1);

                deptsInvolved.Add(deptCode);
                    context.SaveChanges();
            }
            }
            int retrievalId = disbursementItems.First().RetrievalID;

            List<DisbursementItem> dbDisbItems = (from x in context.DisbursementItems
                                                  where x.RetrievalID == retrievalId
                                                  orderby x.RetrievalID
                                                  select x).ToList();

            var q = (from x in context.DisbursementLists
                join e in context.Employees on x.RepresentativeID equals e.EmpID
                join d in context.Departments on e.DeptCode equals d.DeptCode
                where deptsInvolved.Contains(d.DeptCode)
                group x.Id by d.DeptCode
                into grp
                         select new { DeptCode = grp.Key, Id = grp.Max() });



            var insertedDisbListIds = q.ToList();

            foreach (DisbursementItem dbDisbItem in dbDisbItems)
            {
                foreach (DisbursementItem disbItem in disbursementItems)
                {
                    // find your row to update 
                    if (dbDisbItem.ItemCode.Equals(disbItem.ItemCode) && dbDisbItem.DeptCode.Equals(disbItem.DeptCode) && dbDisbItem.RetrievalID.Equals(disbItem.RetrievalID))
                    {
                        dbDisbItem.RetrievedQuantity = disbItem.RetrievedQuantity;

                        if (status.Equals("finalize"))
                        {
                        // find your DisbursementID to update 
                        foreach (var insertedDisbListId in insertedDisbListIds)
                        {
                            if (insertedDisbListId.DeptCode.Equals(disbItem.DeptCode) && retrievalId.Equals(disbItem.RetrievalID))
                            {
                                dbDisbItem.DisbursementID = insertedDisbListId.Id;
                            }
                        }
                    }
                }
            }
            }

            //
           RetrievalByItemList = receiveRetrievalByItem();

           foreach (RetrievalByItem r in RetrievalByItemList)
            {
                var currentRetrievalId = r.RetrievalID;
                var currentItemCode = r.ItemCode;

                var filtered = disbursementItems.Where(i => (i.ItemCode == currentItemCode && r.RetrievalID == currentRetrievalId));
                int sum = (int)filtered.Sum(i => i.RetrievedQuantity);

                r.RetrievedQty = sum;
                //r.Status = "Retrieved";
            }

            if (status.Equals("finalize"))
            {
                foreach (RetrievalByItem r in RetrievalByItemList)
                {
                    r.Status = "Retrieved";
                }

                var requisition = from r in context.Requisitions
                                  where r.Status == "Retrieval"
                                  select r;

                foreach (Requisition r in requisition)
                {
                    r.Status = "Retrieved";
                }

                var disbByItem = from r in context.DisbursementItems
                                  where r.Status == "PartiallyRetrieved"
                                  select r;

                foreach (DisbursementItem d in disbByItem)
                {
                    d.Status = "Retrieved";
                }
            }

            return (context.SaveChanges() > 0);
        }



        public Employee getDeptHead(String deptCode)
        {
            Employee emp = (from e in context.Employees
                where e.DeptCode == deptCode && e.Role == "DeptHead"
                select e).FirstOrDefault();

            return emp;
        }

        // Get DiscrepancyVoucherList (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getDiscrepancyVoucherList()
        {
            var disVouList = from x in context.Discrepancies
                group x by x.VoucherID
                into Voucher
                orderby Voucher.Key
                select Voucher.FirstOrDefault();

            if (disVouList != null)
            {
                try
                {
                    return disVouList.ToList();
                }
                catch (System.ArgumentNullException e)
                {
                    Console.WriteLine("System.ArgumentNullException: {0}", e.Source);
                    return null;
                }
            }
            else
                return null;
        }

        // Get DiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getDiscrepancyVoucherItems(String vouID)
        {
            var disItemList = from x in context.Discrepancies
                where x.VoucherID == vouID
                select x;

            if (disItemList != null)
            {
                return disItemList.ToList();
            }
            else
                return null;
        }

        // Get AppDisVoucherList (Return List<Discrepancy> or Null) // Benny
        // Only Get By ApprovedBy and Status = Pending
        public List<Discrepancy> getAppDisVoucherList(String appID)
        {
            var appDisVouList = from x in context.Discrepancies
                where x.ApprovedBy == appID && x.Status == "Pending"
                group x by x.VoucherID
                into Voucher
                orderby Voucher.Key
                select Voucher.FirstOrDefault();

            if (appDisVouList != null)
            {
                try
                {
                    return appDisVouList.ToList();
                }
                catch (System.ArgumentNullException e)
                {
                    Console.WriteLine("System.ArgumentNullException: {0}", e.Source);
                    return null;
                }
            }
            else
                return null;
        }

        // Get PendingDiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getPendDisVoucherItems(String vouID)
        {
            var disItemList = from x in context.Discrepancies
                where x.VoucherID == vouID && x.Status == "Pending"
                select x;

            if (disItemList != null)
            {
                return disItemList.ToList();
            }
            else
                return null;
        }

        // Set DiscrepancyList (Return True or False) // Benny
        // Used to update status of entire List<Discrepancy>.
        public bool setDiscrepancyList(List<Discrepancy> disList)
        {
            int count = 0;
            foreach (Discrepancy d in disList)
            {
                var q = from x in context.Discrepancies
                    where x.DiscrepancyID == d.DiscrepancyID
                    select x;

                Discrepancy old = q.FirstOrDefault();

                if (old != null)
                {
                    old.Status = d.Status;
                    count += context.SaveChanges();
                }
            }

            if (count == disList.Count)
            {
                return true;
            }
            else
                return false;
        }

        // Set Discrepancy (Return True or False) // Benny
        // Used to update status.
        public bool setDiscrepancy(Discrepancy dis)
        {
            var d = from x in context.Discrepancies
                where x.DiscrepancyID == dis.DiscrepancyID
                select x;

            Discrepancy old = d.FirstOrDefault();

            int count = 0;
            if (old != null)
            {
                old.Status = dis.Status;
                count = context.SaveChanges();
            }

            if (count == 1)
            {
                return true;
            }
            else
                return false;
        }

        // Smita
        public List<Object> getRequistionByDepartment()
        {
            /*var groupedRetDetails = from reqDetails in context.RequisitionDetails
                                    where reqDetails.Requisition.ApprovalStatus == "Approved" && reqDetails.Requisition.Status == "Retrieval"//) ||(reqDetails.Requisition.ApprovalStatus == "Approved" &&  reqDetails.Requisition.Status == "Retrieval")
                                    group reqDetails by reqDetails.Requisition.Employee.DeptCode into g
                                    select new { deptCode = g.Key, NoOfRequisition = g.Count() };
            List<Object> retByDeptList = new List<Object>();
            foreach (var retDetail in groupedRetDetails)
            {
                Department dept = getDepartmentById(retDetail.deptCode);
                String Representative = getDepRepresentative(retDetail.deptCode);
                retByDeptList.Add(new { DeptartmentName = dept.Name, TotalRequests = retDetail.NoOfRequisition, Representative = Representative, CollectionPoint = dept.CollectionPoint1.Location });
            }
            return retByDeptList;*/

            var groupedRetDetails = from req in context.Requisitions
                where
                    (req.ApprovalStatus == "Approved" && req.Status == "New") ||
                    (req.ApprovalStatus == "Approved" && req.Status == "Retrieval")
                group req by req.Employee.DeptCode
                into g
                                        select new { deptCode = g.Key, NoOfRequisition = g.Count() };
            List<Object> retByDeptList = new List<Object>();
            foreach (var req in groupedRetDetails)
            {
                Department dept = getDepartmentById(req.deptCode);
                String Representative = getDepRepresentative(req.deptCode);
                retByDeptList.Add(
                    new
                    {
                        DeptartmentName = dept.Name,
                        TotalRequests = req.NoOfRequisition,
                        Representative = Representative,
                        CollectionPoint = dept.CollectionPoint1.Location
                    });
            }
            return retByDeptList;
        }

        //Retrieval list where status is Pending.
        /*public RetrievalByItem getPendingRetrievalList()
        {
            RetrievalByItem retrieval = (from r in context.RetrievalByItems
                                         where r.Status == "Pending"
                                         select r).FirstOrDefault();
            return retrieval;
        }*/

        public Department getDepartmentById(String deptId)
        {
            Department selectedDept = (from d in context.Departments
                where d.DeptCode == deptId
                select d).FirstOrDefault();
            return selectedDept;
        }

        public RetrievalByItem getLastRetrieval()
        {
            RetrievalByItem lastRetrieval = (from r in context.RetrievalByItems
                orderby r.RetrievalID descending
                select r).FirstOrDefault();
            return lastRetrieval;
        }

        public String getDepRepresentative(String deptCode)
        {
            Employee emp = (from e in context.Employees
                where e.DeptCode == deptCode && e.AdditionalRole == "Representative"
                select e).FirstOrDefault();

            return emp.Name;
        }

        public List<DisbursementItem> getDisburseItem(int retrievalId)
        {
            var disburse = from r in context.DisbursementItems
                where r.RetrievalID == retrievalId
                select r;
            List<DisbursementItem> listPendingDisb = disburse.ToList<DisbursementItem>();
            return listPendingDisb;
        }

        public List<RetrievalByItem> getRetrievalById(int retId)
        {
            List<RetrievalByItem> retList = (from r in context.RetrievalByItems
                where r.RetrievalID == retId
                select r).ToList();
            return retList;

        }

        public void deleteDisburseItem(DisbursementItem disburseItem)
        {
            DisbursementItem v = (from d in context.DisbursementItems
                where
                    d.RetrievalID == disburseItem.RetrievalID && d.ItemCode == disburseItem.ItemCode &&
                    d.DeptCode == disburseItem.DeptCode
                select d).FirstOrDefault();
            context.DisbursementItems.DeleteObject(v);
            context.SaveChanges();
            context.AcceptAllChanges();
        }

        public void deleteRetrievalList(RetrievalByItem ret)
        {
            RetrievalByItem v = (from d in context.RetrievalByItems
                where d.RetrievalID == ret.RetrievalID && d.ItemCode == ret.ItemCode
                select d).FirstOrDefault();
            context.RetrievalByItems.DeleteObject(v);
            context.SaveChanges();
            context.AcceptAllChanges();
        }

        public List<DisbursementItem> getRequistionByDepartmentItem1()
        {
            var groupedDisbItem = from reqDetails in context.RequisitionDetails
                where
                    reqDetails.Requisition.ApprovalStatus == "Approved" &&
                    (reqDetails.Requisition.Status == "New" || reqDetails.Requisition.Status == "Retrieval")
                                  group reqDetails by new { reqDetails.ItemCode, reqDetails.Requisition.Employee.DeptCode }
                into g
                                      select new { deptCode = g.Key.DeptCode, itemCode = g.Key.ItemCode, requestedQty = g.Sum(r => r.Quantity) };
            List<DisbursementItem> retByDeptList = new List<DisbursementItem>();
            foreach (var disbItem in groupedDisbItem)
            {
                Department dept = getDepartmentById(disbItem.deptCode);
                String Representative = getDepRepresentative(disbItem.deptCode);
                retByDeptList.Add(new DisbursementItem
                {
                    DeptCode = dept.DeptCode,
                    RequestedQty = disbItem.requestedQty,
                    ItemCode = disbItem.itemCode
                });
            }
            return retByDeptList;
        }

        public List<RetrievalByItem> getPartiallyRetrievedRetrieval()
        {
            var v = from r in context.RetrievalByItems
                where r.Status == "PartiallyRetrieved"
                select r;
            List<RetrievalByItem> partialRetrieval = v.ToList<RetrievalByItem>();
            return partialRetrieval;
        }

        public int updateDisbQuantity(DisbursementItem disb)
        {
            DisbursementItem disburse = (from d in context.DisbursementItems
                where d.RetrievalID == disb.RetrievalID && d.DeptCode == disb.DeptCode && d.ItemCode == disb.ItemCode
                select d).FirstOrDefault();
            disburse.RequestedQty = disb.RequestedQty;
            return context.SaveChanges();
        }

        public List<RetrievalByItem> getRequisitionForConsolidation()
        {
            var groupedRetDetails = from reqDetails in context.RequisitionDetails
                where
                    reqDetails.Requisition.ApprovalStatus == "Approved" &&
                    (reqDetails.Requisition.Status == "New" || reqDetails.Requisition.Status == "Retrieval")
                group reqDetails by reqDetails.ItemCode
                into g
                                        select new { itemCode = g.Key, Qty = g.Sum(r => r.Quantity) };

            List<RetrievalByItem> retByItemList = new List<RetrievalByItem>();
            foreach (var reqDetail in groupedRetDetails)
            {
                int retrievedQty = 0;
                Item item = getItembyId(reqDetail.itemCode);
                retByItemList.Add(new RetrievalByItem
                {
                    ItemCode = item.ItemCode,
                    RequestedQty = reqDetail.Qty,
                    RetrievedQty = retrievedQty
                });
            }
            List<Requisition> listRequisition = getRequisitionsByStatus();
            foreach (Requisition req in listRequisition)
            {
                updateRequisitionStatus("Retrieval", req);
            }
            return retByItemList;
        }

        public Item getItembyId(String itemCode)
        {
            Item selectedItem = (from i in context.Items
                where i.ItemCode == itemCode
                select i).FirstOrDefault();
            return selectedItem;
        }

        public List<Requisition> getRequisitionsByStatus()
        {
            var requisition = from r in context.Requisitions
                where r.Status == "New" && r.ApprovalStatus == "Approved"
                select r;
            List<Requisition> listRequisition = requisition.ToList<Requisition>();
            return listRequisition;
        }

        public int updateRequisitionStatus(String statusRetrieved, Requisition requisition)
        {
            Requisition req = (from r in context.Requisitions
                where r.RequisitionID == requisition.RequisitionID
                select r).FirstOrDefault();
            req.Status = statusRetrieved;
            return context.SaveChanges();

        }

        public int insertNewRetrieval(RetrievalByItem retrievals)
        {
            context.RetrievalByItems.AddObject(retrievals);
            int noOfRowsAffected = context.SaveChanges();
            return noOfRowsAffected;
        }

        public int insertDisbursementItem(DisbursementItem disbItem)
        {
            context.DisbursementItems.AddObject(disbItem);
            return context.SaveChanges();
        }

        public List<DisbursementItem> getPartiallyRetrievedDisburs()
        {
            var v = from r in context.DisbursementItems
                where r.Status == "PartiallyRetrieved"
                select r;
            List<DisbursementItem> partialRetrieval = v.ToList<DisbursementItem>();
            return partialRetrieval;
        }

        // StoreController
        public List<Supplier> getSupplierList()
        {
            var q = from s in context.Suppliers
                select s;

            return q.ToList();
        }

        // StoreController
        public Supplier getSupplierByID(String supID)
        {
            var q = from s in context.Suppliers
                where s.SupplierID == supID
                select s;

            return q.FirstOrDefault();
        }


    // StoreController
        public List<ItemSupplierDetail> getItemSupplierListBySupID(String supID)
        {
            var q = from i in context.ItemSupplierDetails
                    where i.SupplierID == supID
                    select i;

            return q.ToList();
        }

        // StoreController // Delete Supplier if no ItemSupplier records. // Benny
        public bool delSupplier(String supID)
        {
            if (getItemSupplierListBySupID(supID).Count == 0)
            {
                Supplier s = getSupplierByID(supID);
                context.DeleteObject(s);
                context.SaveChanges();
                return true;
            }
            else
                return false;
        }

        // StoreController // Insert new Supplier if not exist. // Benny
        public bool addSupplier(Supplier sup)
        {
            if (getSupplierByID(sup.SupplierID) == null)
            {
                context.Suppliers.AddObject(sup);
                if (context.SaveChanges() == 1)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }

        // StoreController // Set Supplier if Exist. // Benny
        public bool setSupplier(Supplier sup)
        {
            Supplier old = getSupplierByID(sup.SupplierID);

            if (old != null)
            {
                old.Name = sup.Name;
                old.Address = sup.Address;
                old.FaxNumber = sup.FaxNumber;
                old.ContactName = sup.ContactName;
                old.PhoneNumber = sup.PhoneNumber;

                if (context.SaveChanges() == 1)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }

        // StoreController // Get ItemCategoryList. // Benny
        public List<String> getItemCategoryList()
        {
            List<String> list = new List<String>();

            var q = from x in context.Items
                group x by x.Category
                into Category
                orderby Category.Key
                select Category.FirstOrDefault();

            List<Item> itemList = q.ToList();

            foreach (Item i in itemList)
            {
                list.Add(i.Category);
            }

            if (list != null)
                return list;
            else
                return null;
        }

        // StoreController // Get ItemListByCategory. // Benny
        public List<Item> getItemListByCategory(String cat)
        {
            // List<String> list = new List<String>();

            var q = from x in context.Items
                where x.Category == cat
                select x;

            return q.ToList();
        }

        // StoreController // Insert New Item. // Benny
        public bool addItem(Item item)
        {
            context.Items.AddObject(item);
            if (context.SaveChanges() == 1)
                return true;
            else
                return false;
        }

        // StoreController // Set setItem. // Benny
        public bool setItem(Item item)
        {
            Item old = getItembyId(item.ItemCode);

            if (old != null)
            {
                old.Category = item.Category;
                old.Description = item.Description;
                old.ReOrderLevel = item.ReOrderLevel;
                old.ReOrderQuantity = item.ReOrderQuantity;
                old.UnitOfMeasure = item.UnitOfMeasure;
                //old.BinId = item.BinId;
                old.Status = item.Status;

                if (context.SaveChanges() == 1)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }

        // StoreController // Get getItemSupplierList. // Benny
        public List<ItemSupplierDetail> getItemSupplierList(String itemCode)
        {
            Item item = getItembyId(itemCode);
            List<ItemSupplierDetail> list = item.ItemSupplierDetails.ToList();

            if (list != null)
                return list;
            else
                return null;
        }

        // StoreController // Set setItemSupplierList. // Benny
        public bool setItemSupplierList(List<ItemSupplierDetail> list, String ICode)
        {
            int count = list.Count;
            int save = 0;
            List<ItemSupplierDetail> oldList = getItemSupplierList(ICode);
            if (count != 0)
            {
                for (int x = 0; x < oldList.Count; x++)
                {
                    if (count > 0)
                    {
                        //list[count - 1].Id = tempID;
                        oldList[x].ItemCode = list[count - 1].ItemCode;
                        oldList[x].SupplierID = list[count - 1].SupplierID;
                        oldList[x].UnitPrice = list[count - 1].UnitPrice;
                        if (context.SaveChanges() == 1)
                        {
                            count--;
                            save++;
            }
                    }
                    else
                        deleteItemSupplier(oldList[x].Id);
                }

                for (int x = count; x > 0; x--)
                {
                    //list[count - 1].Id = getNextItemSupplierID();
                    context.ItemSupplierDetails.AddObject(list[x - 1]);
                    if (context.SaveChanges() == 1)
                        save++;
                }
                if (save == list.Count)
            return true;
                else
                    return false;
        }
            else
            {
                foreach (ItemSupplierDetail i in oldList)
                    deleteItemSupplier(i.Id);
                return true;
            }
        }

        // StoreController // Delete ItemSupplierDetail. //Benny
        public bool deleteItemSupplier(int id)
        {
            var q = from x in context.ItemSupplierDetails
                where x.Id == id
                select x;

            context.ItemSupplierDetails.DeleteObject(q.FirstOrDefault());
            if (context.SaveChanges() == 1)
                return true;
            else
                return false;
        }

        //// StoreController // Get getNextItemSupplierID. // Benny
        //public int getNextItemSupplierID()
        //{
        //    var q = from x in context.ItemSupplierDetails
        //        orderby x.Id descending
        //        select x;

        //    ItemSupplierDetail last = q.FirstOrDefault();
        //    if (last != null)
        //        return last.Id + 1;
        //    else
        //        return 1;
        //}

        //To add new transaction...Smita
        public int addTransaction(Transaction trans)
        {
            context.Transactions.AddObject(trans);
            int rowsAffected = context.SaveChanges();
            return rowsAffected;
        }
        //To update inventory .... Smita
        public int UpdateInventory(String itemCode, int? qty)
        {
            Item item = getItembyId(itemCode);
            item.CurrentQuantity = qty;

            int rowsAffected = context.SaveChanges();
            if (rowsAffected > 0)
            {
                Item i = getItembyId(itemCode);
                if (item.Status == "Ordered" && item.CurrentQuantity > item.ReOrderLevel)
                {
                    item.Status = "Active";
                    rowsAffected = context.SaveChanges();
        }
            }
            return rowsAffected;
        }
        //To get last Transaction ID...Smita
        public String getLastTransID()
        {
            String lastTransID = (from t in context.Transactions
                                  orderby t.TransId descending
                                  select t.TransId).FirstOrDefault<String>();
            return lastTransID;
        }
        public PurchaseOrder getPurchaseOrderByID(int PONum)
        {
            PurchaseOrder PO = (from o in context.PurchaseOrders
                                where o.PurchaseOrderNum == PONum
                                select o).FirstOrDefault();
            return PO;

        }

        //To get Last discrepancyId...Smita
        public int getLastDiscrepancyID()
        {
            int discID = (from d in context.Discrepancies
                          orderby d.DiscrepancyID descending
                          select d.DiscrepancyID).FirstOrDefault();
            return discID;
        }

        //To add new discripancy...Smita
        public int addDiscrepancy(Discrepancy newDisc)
        {

            context.Discrepancies.AddObject(newDisc);
            int rowsAffected = context.SaveChanges();
            return rowsAffected;
        }

        //To get Store manager id.
        public Employee getStoreManager()
        {
            Employee emp = (from e in context.Employees
                            where e.DeptCode == "STOR" && e.Role == "Manager"
                            select e).FirstOrDefault();
            return emp;
        }
        //To get Store Supervisor id.
        public Employee getStoreSupervisor()
        {
            Employee emp = (from e in context.Employees
                            where e.DeptCode == "STOR" && e.Role == "Supervisor"
                            select e).FirstOrDefault();
            return emp;
        }
        //To get last running transaction  ID...Smita
        public int getRunningTransID()
        {
            List<String> TransID = (from t in context.Transactions
                                  select t.TransId).ToList();
            List<int> listNum = new List<int>();
            int runningNum = 1;
            foreach (String id in TransID)
            {
                listNum.Add(Convert.ToInt32(id.Split('/')[0].Substring(5)));
            }
            if (listNum.Any())
            {
                listNum.Sort();
                runningNum = listNum.Last();
            }
            return runningNum;
        }

        // To get Last running discrepancy id....Smita
        public int getRunningVoucherID()
        {
            List<String> listDisc = (from d in context.Discrepancies
                           select d.VoucherID).ToList();
            List<int> listNum = new List<int>();
            int runningNum = 1;
            foreach (String id in listDisc)
            {
                String s = id.Split('/')[0];
                s = s.Substring(1);
                listNum.Add(Convert.ToInt32(s));
                listNum.Sort();
                runningNum =  listNum.Last();
            }
            return runningNum;
        }

        //To update Status of purchase order..Status will be changed as 'Close'.
        public int ClosePurchaseOrder(PurchaseOrder PO)
        {
            PurchaseOrder pOrder = (from p in context.PurchaseOrders
                    where p.PurchaseOrderNum == PO.PurchaseOrderNum
                    select p).FirstOrDefault();
            pOrder.Status = "Closed";
            return context.SaveChanges();
        }
        //To update deliveryID and delivery Date.
        public void updatePODelivery(PurchaseOrderDetail pDetail)
        {
            PurchaseOrderDetail pOrderDetails = (from pd in context.PurchaseOrderDetails
                                    where pd.PurchaseOrderNum == pDetail.PurchaseOrderNum && pd.ItemCode == pDetail.ItemCode
                                    select pd).FirstOrDefault();
            pOrderDetails.DeliveryID = pDetail.DeliveryID;
            pOrderDetails.DeliveryDate = pDetail.DeliveryDate;
            context.SaveChanges();
        }

        //To get purchase order details by passing itemcode and PONumber 
        public PurchaseOrderDetail getPODetails(String itemCode, int PONumber)
        {
            PurchaseOrderDetail pOrderDetails = (from pd in context.PurchaseOrderDetails
                                                 where pd.PurchaseOrderNum == PONumber && pd.ItemCode == itemCode
                                                 select pd).FirstOrDefault();
            return pOrderDetails;
        }

        public bool approveDisbursement(List<DisbursementItem> di, String deptCode)
        {
                foreach (DisbursementItem d in di)
                {
                var dis = getDeptDisbItem((int)d.DisbursementID,d.ItemCode);
                if (d.RetrievedQuantity == dis.RequestedQty)
                    {
                    dis.Status = "Retrieved";
                    }
                    else
                    {
                    dis.Status = "Partially Retrieved";
                    }
               int i =  context.SaveChanges();
                }
                if (context.SaveChanges() != 0)
                {
                    return true;
                }
                else
                    return false;
            }





        public string getDeptCodewithName(string deptName)
        {
            return ((from x in context.Departments where x.Name == deptName select x.DeptCode).First()).ToString();
        }
        public int getNewRequisitionCount()
        {
            List<Requisition> listReq = (from r in context.Requisitions
                     where r.Status == "New" && r.ApprovalStatus == "Approved"
                     select r).ToList<Requisition>();
            if (listReq != null)
            {
              return listReq.Count;
        }
            else
            {
                return 0;
            }
        }


        public List<Discrepancy> getPendingDiscrepancy()
        {
            List<Discrepancy> pendingDisVouList = (from x in context.Discrepancies
                                                   where x.Status == "Pending"
                                                   select x).ToList();
            return pendingDisVouList;

        }


        public List<Requisition> getPendingRequisitions(String DeptCode)
        {
            var getPendingRequisitions = from x in context.Requisitions
                                         where (x.Status == "Pending" && x.Employee.DeptCode == DeptCode)
                                         select x;
            return getPendingRequisitions.ToList();
        }

        public DisbursementList getReadyDisbursements(String DeptCode)
        {
            var readyDisbist = (from x in context.DisbursementLists
                               where (x.Status == "Ready" && x.Employee.DeptCode== DeptCode)
                               select x).FirstOrDefault();

            if (readyDisbist != null)
            {
                return readyDisbist;
            }
            else
                return null;
        }
        public Employee getEmployeebyRole()
        {
            return (from x in context.Employees where x.Role == "Supervisor" select x).First();
        }
        public Employee getManager()
        {
            return (from x in context.Employees where x.Role == "Manager" select x).First();
        }
        
    }
}

