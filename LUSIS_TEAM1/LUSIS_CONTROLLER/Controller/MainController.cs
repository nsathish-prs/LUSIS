using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;
using System.Net.Mail;
using System.Net;
namespace LUSIS_CONTROLLER.Controller
{

    public class MainController
    {

        // NOTES :: CONTROLLER FUNCTIONS LISTED IN ALPHA ORDER.

        // DeptController controller = new DeptController();
        // DisburseController disburseController = new DisburseController();
        // DiscrepancyController controller = new DiscrepancyController();
        // EmailController controller = new EmailController();
        // InventoryController controller = new InventoryController();
        // LoginController controller = new LoginController();
        // PurchaseController controller = new PurchaseController();
        // ReportController Controller = new ReportController();
        // RequisitionController controller = new RequisitionController();
        // RetrievalController controller = new RetrievalController();
        // StoreController controller = new StoreController();

        // DeptController // (Return Employee = DelegateEmp) Or Null // Benny
        public Employee getDelegateEmp(String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getDelegateEmp(deptCode);
        }

        // DeptController // Get Delegate By String (deptCode) (Return Delegate Or Null) // Benny
        public LUSIS_EF_FACADE.Delegate getDelegate(String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getDelegate(deptCode);
        }

        // DeptController // Clear Delegate By String (deptCode) // Benny
        public Boolean clearDelegate(String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.clearDelegate(deptCode);
        }

        // DeptController // Update Delegate By String (delName, deptCode, startDate, endDate) // Benny
        public Boolean updateDelegate(String delName, String deptCode, String startDate, String endDate)
        {
            DeptController deptCont = new DeptController();
            return deptCont.updateDelegate(delName, deptCode, startDate, endDate);
        }

        // DeptController (Return List<CollectionPoint> Or Null) // Benny
        public List<CollectionPoint> getAllCPointsLocation()
        {
            DeptController deptCont = new DeptController();
            return deptCont.getAllCPointsLocation();
        }

        // DeptController (Return CollectionPoint Or Null) // Benny
        public CollectionPoint getDeptCPointLocation(String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getDeptCPointLocation(deptCode);
        }

        // DeptController (Return List<Employee> For "Delegate" or "Representative" Or Null) // Benny
        public List<Employee> getDeptEmployees(String deptCode, String addRole)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getDeptEmployees(deptCode, addRole);
        }

        // DeptController (Return Employee Or Null) // Benny
        public Employee getDeptRepName(String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getDeptRepName(deptCode);
        }

        // DeptController Take 4 String, Batch Processing, (Return bool = True Or False) // Benny
        public bool updateDeptDetails(String prevRep, String newRep, String newCPoint, String deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.UpdateDeptDetails(prevRep, newRep, newCPoint, deptCode);
        }

       // Receive retrieval by Item
        public List<RetrievalByItem> receiveRetrievalByItem()
        {
            DisburseController disCont = new DisburseController();
            List<RetrievalByItem> retrievalItems = disCont.receiveRetrievalByItem();
            return retrievalItems;
        }

        public List<DisbursementList> getDeptDisbList()
        {
            DisburseController disCont = new DisburseController();
            List<DisbursementList> disbList = disCont.getDeptDisbList();
            return disbList;
        }

        public List<DisbursementList> getDeptDisbList(String deptCode)
        {
            DisburseController disCont = new DisburseController();
            List<DisbursementList> disbList = disCont.getDeptDisbList(deptCode);
            return disbList;
        }

        public List<DisbursementItem> getDeptDisbItemList(int disbursementID)
        {
            DisburseController disCont = new DisburseController();
            List<DisbursementItem> disbursementItems = (disCont.getDeptDisbItemList(disbursementID));
            return disbursementItems;
        }

        
      public Boolean generateDisb(List<DisbursementItem> disbursementItems, Dictionary<String, DisbursementList> deptDisbLists,String status)
        {
            DisburseController disCont = new DisburseController();
            return disCont.generateDisb(disbursementItems, deptDisbLists,status);
        }

        //// DisburseController -- SUCHITA
        public void CancelDisbursement(int selectedDisbID)
        {
            DisburseController disCont = new DisburseController();
            disCont.CancelDisbursement(selectedDisbID);
        }

        

        // DiscrepancyController // Benny
        // Get ApproveDiscrepancyVoucherList (Return List<Discrepancy> or Null) //Benny
        public List<Discrepancy> getAppDisVoucherList(String appID)
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.getAppDisVoucherList(appID);
        }

        // DiscrepancyController // Benny
        // Get DiscrepancyVoucherList (Return List<Discrepancy> or Null)
        public List<Discrepancy> getDiscrepancyVoucherList()
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.getDiscrepancyVoucherList();
        }

        // DiscrepancyController // Benny
        // Get DiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null)
        public List<Discrepancy> getDiscrepancyVoucherItems(String vouID)
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.getDiscrepancyVoucherItems(vouID);
        }

        // DiscrepancyController // Benny
        // Get PendingDiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null)
        public List<Discrepancy> getPendDisVoucherItems(String vouID)
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.getPendDisVoucherItems(vouID);
        }

        // DiscrepancyController // Benny
        // Set DiscrepancyList (Return True or False)
        // Used to update status of a list of Discrepancy.
        public bool setDiscrepancyList(List<Discrepancy> disList)
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.setDiscrepancyList(disList);
        }

        // DiscrepancyController // Benny
        // Set Discrepancy (Return True or False) // Benny
        // Used to update status of a Discrepancy.
        public bool setDiscrepancy(Discrepancy dis)
        {
            DiscrepancyController disCont = new DiscrepancyController();
            return disCont.setDiscrepancy(dis);
        }

        // LoginController
        public Employee checkLogin(String uname, String passwd)
        {
            LoginController loginCont = new LoginController();
            return loginCont.checkLogin(uname, passwd);
        }

        public Employee getEmployeeRecord(string uName)
        {
            LoginController loginCont = new LoginController();
            return loginCont.getEmployeeRecord(uName);
        }

        public bool updatePassword(string uName, string password)
        {
            LoginController loginCont = new LoginController();
            if (loginCont.updatePassword(uName, password))
                return true;
            else
                return false;
        }


        // PurchaseController
        public string getItemDesc(string iCode)
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getItemDesc(iCode);
        }

        // PurchaseController
        public int getItemMinOrder(string iCode)
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getItemMinOrder(iCode);
        }

        // PurchaseController
        public bool updatePODetail(int pod)
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.updatePODetail(pod);
        }

        // PurchaseController
        public string getsupID(string sName)
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getsupID(sName);
        }

        // PurchaseController
        public List<PurchaseOrder> getPOdetails()
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getPOdetail();
        }

        // PurchaseController
        public void addPurchaseOrder(PurchaseOrder po)
        {
            PurchaseController purCont = new PurchaseController();
            purCont.addnewPO(po);
        }

        // PurchaseController
        public void addPurchaseOrderDetails(PurchaseOrderDetail pod)
        {
            PurchaseController purCont = new PurchaseController();
            purCont.addPODetail(pod);
        }

        // PurchaseController
        public void updateItem(Item item, String iCode)
        {
            PurchaseController purCont = new PurchaseController();
            purCont.updateItem(item, iCode);
        }

        // PurchaseController
        public int getLastRecord()
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getLastRecord();
        }

        // PurchaseController
        public List<Item> getPurchaseRequired()
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.itemList();
        }

        // PurchaseController
        public List<Item> purchaseList()
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.purchaseItems();
        }

        // PurchaseController
        public List<PurchaseOrderDetail> getPODetails(string pid)
        {
            PurchaseController purCont = new PurchaseController();
            int pID = Convert.ToInt32(pid);
            return purCont.getPODetails(pID);
        }

        // PurchaseController
        public PurchaseOrder getPOList(string pod)
        {
            PurchaseController purCont = new PurchaseController();
            int pOD = Convert.ToInt32(pod);
            return purCont.getPOList(pOD);
        }

        // PurchaseController
        public string getSupName(string sID)
        {
            PurchaseController purCont = new PurchaseController();
            return purCont.getSupName(sID);
        }


        // ReportController // Giri
        public List<Item> getdistItemList()
        {
            
            ReportController RepCont = new ReportController();
            return RepCont.getdistItemList();
        }

        // ReportController // Giri
        public List<string> getdeptList()
        {
            ReportController RepCont = new ReportController();
            return RepCont.getdeptList();
        }

        // ReportController // Giri
        public List<string> getItemsbyDesc(string selected)
        {
            ReportController RepCont = new ReportController();
            return RepCont.getItemsbyDesc(selected);
        }

        // ReportController // Giri
        public string getItemByDescr(String description)
        {
            ReportController RepCont = new ReportController();
            return RepCont.getItemByDescr(description);
        }

        // ReportController // Giri
        public string getDeptCodewithName(string deptName)
        {
            ReportController RepCont = new ReportController();
            return RepCont.getDeptCodewithName(deptName);
        }

        // RequisitionController
        public List<Item> getItemCatalog()
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getItemCatalog();
        }
        // RequisitionController 
        // Search by Item Description
        public List<Item> getSearchItemCatalog(String key)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getSearchItemCatalog(key);
        }

        // RequisitionController 
        // To select an Item details by passing item Desciption.
        public Item selectItemByDescription(String description)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.selectItemByDescription(description);
        }

        // RequisitionController 
        // Insert record for new request
        public int addNewRequisition(Requisition req)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.addNewRequisition(req);
        }

        // RequisitionController 
        // Add NewRequisitionDetail (Single)
        public int addNewRequisitionDetails(RequisitionDetail reqDetail)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.addNewRequisitionDetail(reqDetail);
        }

        // RequisitionController 
        // Delete ReqDetailsByReqID
        public int deleteReqDetailsByReqID(String ReqID)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.deleteReqDetailsByReqID(ReqID);
        }

        // RequisitionController
        public Employee selectEmployeeByID(String empID)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.selectEmployeeByID(empID);
        }

        // RequisitionController
        public String getNewRequisitionId(String empID)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getNewRequisitionId(empID);
        }

        // RequisitionController // Benny
        // Get DeptRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptRequisitionList(String dept)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getDeptRequisitionList(dept);
        }

        // RequisitionController // Benny
        // Get EmpRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getEmpRequisitionList(String dept)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getEmpRequisitionList(dept);
        }

        // RequisitionController // Benny
        // Get DeptAppRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptAppRequisitionList(String dept)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getDeptAppRequisitionList(dept);
        }

        // RequisitionController // Benny
        // Get RequisitionByID (Return Requisition or Null)
        public Requisition getRequisitionByID(String reqID)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getRequisitionByID(reqID);
        }

        // RequisitionController
        // Set Requisition (Return Requisition or Null)
        public Requisition setRequisition(Requisition req)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.setRequisition(req);
        }

        // RequisitionController 
        // Get RequisitionDetailsByRequisitionID (Return List<RequisitionDetail> or Null)
        public List<RequisitionDetail> getReqDetailsByReqID(String reqID)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.getReqDetailsByReqID(reqID);
        }

        // RequisitionController 
        // createNewRequisitionWithDetails() (Return bool)
        public bool createNewRequisitionWithDetails(Employee emp, String reqID, String comments, List<RequisitionDetail> reqDetList)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.createNewRequisitionWithDetails(emp, reqID, comments, reqDetList);
        }

        // RequisitionController
        public Employee selectDeptHead(String deptId)
        {
            RequisitionController reqCont = new RequisitionController();
            return reqCont.selectDeptHead(deptId);
        }

        // RetrievalController
        // To get requisitions by each department. (DeptName, NoOfReqsts, Representative and collection point.)....Smita
        public List<Object> selectRequistionByDepartment()
        {
            RetrievalController retCont = new RetrievalController();
            return retCont.selectRequistionByDepartment();
            
        }

        // RetrievalController
        //To generate retrievalList( consolidated based on Items) .
        //To generate Disbursement Items(Dept and Item)....Smita
        public void generateRetrieval()
        {
            RetrievalController retCont = new RetrievalController();
            retCont.generateRetrieval();
        }

        public Employee getEmployee(String empId)
        {
            DeptController deptController = new DeptController();
            return deptController.getEmployee(empId);
        }
        public Employee getEmployeeByNameAndDept(string empName, string deptCode)
        {
            DeptController deptCont = new DeptController();
            return deptCont.getEmployeeByNameAndDept(empName, deptCode);
        }

        // StoreController (Return List<Supplier> = List of Supplier Or Null) // Benny
        public List<Supplier> getSupplierList()
        {
            StoreController storeCont = new StoreController();
            return storeCont.getSupplierList();
        }

        // StoreController (Return Supplier By ID Or Null) // Benny
        public Supplier getSupplierByID(String supID)
        {
            StoreController storeCont = new StoreController();
            return storeCont.getSupplierByID(supID);
        }

        // StoreController (Return List<ItemSupplierDetail> By ID Or Null) // Benny
        public List<ItemSupplierDetail> getItemSupplierListBySupID(String supID)
        {
            StoreController storeCont = new StoreController();
            return storeCont.getItemSupplierListBySupID(supID);
        }

        // StoreController // Delete Supplier if no ItemSupplier records. // Benny
        public bool delSupplier(String supID)
        {
            StoreController storeCont = new StoreController();
            return storeCont.delSupplier(supID);
        }

        // StoreController // Insert new Supplier if not exist. // Benny
        public bool addSupplier(Supplier sup)
        {
            StoreController storeCont = new StoreController();
            return storeCont.addSupplier(sup);
        }

        // StoreController // Set Supplier if Exist. // Benny
        public bool setSupplier(Supplier sup)
        {
            StoreController storeCont = new StoreController();
            return storeCont.setSupplier(sup);
        }

        // StoreController // Get ItemCategoryList. // Benny
        public List<String> getItemCategoryList()
        {
            StoreController storeCont = new StoreController();
            return storeCont.getItemCategoryList();
        }

        // StoreController // Get ItemListByCategory. // Benny
        public List<Item> getItemListByCategory(String cat)
        {
            StoreController storeCont = new StoreController();
            return storeCont.getItemListByCategory(cat);
        }

        // StoreController // Insert New Item. // Benny
        public bool addItem(Item item)
        {
            StoreController storeCont = new StoreController();
            return storeCont.addItem(item);
        }

        // StoreController // Set setItem. // Benny
        public bool setItem(Item item)
        {
            StoreController storeCont = new StoreController();
            return storeCont.setItem(item);
        }

        // StoreController // Get getItemSupplierList. // Benny
        public List<ItemSupplierDetail> getItemSupplierList(String itemCode)
        {
            StoreController storeCont = new StoreController();
            return storeCont.getItemSupplierList(itemCode);
        }

        // StoreController // Set setItemSupplierList. // Benny
        public bool setItemSupplierList(List<ItemSupplierDetail> list, String ICode)

        {
            StoreController storeCont = new StoreController();
            return storeCont.setItemSupplierList(list, ICode);
        }

        //To add discrepancy to discrepancy table....Smita
        //for Discrepancy object assign --->Itemcode,Reason,Quantity,EmpID.
        public int addDiscrepancy(List<Discrepancy> listDiscripancy, string empID)
        {
            DiscrepancyController controller = new DiscrepancyController();
            return controller.addDiscrepancy(listDiscripancy, empID);
        }

        //To add new Transactions...Smita
        /*
      * for deliveryOrder: transDesc-->DeliveryID(format: DO1/SupID/2014, transType-->PurchaseOrderNum,action-->OrderDelivery
      * for discrepancy: transDesc-->voucherID, transType-->discrepancyID ,action-->Discrepancy
      * for disbursement: transDesc-->DeptCode, transType-->DisburseID,action-->Disbursement*/
        public void AddNewTransaction(String itemCode, String transDesc, int transType, int? qty, String action)
        {
            InventoryController controller = new InventoryController();
            controller.AddNewTransaction(itemCode, transDesc, transType, qty, action);
        }

        //Quantity, (+) for DeliveryOrder/Discrepancy, (-) for Discrepancy/Disbursement
        //quantity should be string with '+' or '-' sign.s
        public int UpdateInventory(String itemCode, String quantity)
        {
            InventoryController controller = new InventoryController();
            return controller.UpdateInventory(itemCode, quantity);
        }
        
        //To update delivery status after receiving the delivery...Smita.
        //Pass list of received itemcode.
        public void receiveDelivery(List<String>listItemCode,int PONum,String deliveryID)
        {
            PurchaseController controller = new PurchaseController();
            controller.receiveDelivery(listItemCode, PONum, deliveryID);
        }
        
        public bool approveDisbursement(List<DisbursementItem> di, String deptCode)
        {
            DisburseController dis = new DisburseController();
            
            return dis.approveDisbursement(di, deptCode);
        }

        public int getNewRequisitionCount()
        {
            StoreController controller = new StoreController();
            return controller.getNewRequisitionCount();
        }

        public Employee getDeptHead(string DeptCode)
        {
            DeptController dept=new DeptController();
            return dept.getDeptHead(DeptCode);
        }

        public int getPendingDiscrepancy()
        {
            DiscrepancyController controller = new DiscrepancyController();
            return controller.getPendingDiscrepancy();
        }


        public List<Requisition> getPendingRequisitions(String DeptCode)
        {
            RequisitionController requisitionController = new RequisitionController();
            List<Requisition> reqList = requisitionController.getPendingRequisitions(DeptCode);
            return reqList; 
        }

        public DisbursementList getReadyDisbursements(String DeptCode)
        {
            DisburseController disburseController = new DisburseController();
            DisbursementList disbList = disburseController.getReadyDisbursements(DeptCode);
            return disbList;

        }
        public bool sendEmail(string fromemail, string toEmail, string subject, string msg)
        {
            MailMessage mm = new MailMessage(fromemail, toEmail, subject, msg);
            mm.IsBodyHtml = true;
            SmtpClient smtpClient = new SmtpClient("lynx.iss.nus.edu.sg");
            try
            {
                smtpClient.Send(mm);
                return true;
            }

            catch (Exception)
            {
                return false;
            }

        }
        public Employee getSupervisor()
        {
            PurchaseController pc = new PurchaseController();
            return pc.getEmployeebyRole();
        }
        public Employee getManager()
        {
            PurchaseController pc = new PurchaseController();
            return pc.getManager();
        }
    }
}
