using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;

// NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService" in both code and config file together.

[ServiceContract]
public interface IService
{

    [OperationContract]
    [WebInvoke(UriTemplate = "/login", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    LoginDetails login(LoginDetails loginCred);

    // View Retrieval List
    [OperationContract]
    [WebGet(UriTemplate = "/viewRetrievalList", ResponseFormat = WebMessageFormat.Json)]
    Retrieval_List viewRetrievalList();

    //Modify Retrieval List
    [OperationContract]
    [WebInvoke(UriTemplate = "/updateRetrievalList", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean updateRetrievalList(Retrieval_List retrievalList);

    // Search using query string  - Catalog
    [OperationContract]
    [WebGet(UriTemplate = "/searchItems/?query={key}", ResponseFormat = WebMessageFormat.Json)]
    SearchDetails searchItems(String key);

    // View Requisition for each employee
    [OperationContract]
    [WebInvoke(UriTemplate = "/viewRequisition", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Requisition_List viewRequisition(LoginDetails loginCred);

    // View Rquisition Details by ReqID
    [OperationContract]
    [WebGet(UriTemplate = "/viewRequisitionDetails/?reqID={id}", ResponseFormat = WebMessageFormat.Json)]
    Requisition_Details viewRequisitionDetails(String id);

    // New Requisition from employee
    [OperationContract]
    [WebInvoke(UriTemplate = "/newRequisition", Method = "POST",
        //BodyStyle = WebMessageBodyStyle.WrappedRequest,
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean createNewRequisition(Requisition_Details reqDet);

    // Requition List by Dept
    [OperationContract]
    [WebGet(UriTemplate = "/getDeptRequisitionList/?deptCode={deptcode}", ResponseFormat = WebMessageFormat.Json)]
    List<Requisition_Details> getDeptRequisitionList(String deptCode);

    // Update Dept Details
    [OperationContract]
    [WebInvoke(UriTemplate = "/updateRequisitionStatus", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    bool updateRequisitionStatus(Requisition_Details requisitionDetails);

    //Get Collection Point
    [OperationContract]
    [WebGet(UriTemplate = "/getDeptDetails/?deptCode={deptcode}", ResponseFormat = WebMessageFormat.Json)]
    DeptDetails getDeptDetails(String deptCode);

    // Update Dept Details
    [OperationContract]
    [WebInvoke(UriTemplate = "/updateDeptDetails", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean updateDeptDetails(DeptDetails collPoint);

    // Get Delegate
    [OperationContract]
    [WebGet(UriTemplate = "/getDelegate/?deptCode={deptCode}", ResponseFormat = WebMessageFormat.Json)]
    Delegate getDelegate(String deptCode);

    // Update Delegate
    [OperationContract]
    [WebInvoke(UriTemplate = "/updateDelegate", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean updateDelegate(Delegate delegat);

    // Generate Retrieval List
    [OperationContract]
    [WebGet(UriTemplate = "/generateRetrievalList", ResponseFormat = WebMessageFormat.Json)]
    void generateRetrievalList();

    // Employee List by Dept
    [OperationContract]
    [WebGet(UriTemplate = "/employeeList/?deptCode={deptcode}&addRole={addRole}", ResponseFormat = WebMessageFormat.Json)]
    List<LoginDetails> employeeList(String deptCode, String addRole);

    // Disbursement List 
    [OperationContract]
    [WebGet(UriTemplate = "/disbursementList", ResponseFormat = WebMessageFormat.Json)]
    List<DisbursementList> disbursementList();


    // Disbursement Approval
    [OperationContract]
    [WebInvoke(UriTemplate = "/approveDisbursement", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean approveDisbursement(AppDisbursement appDisbursement);


    // Discrepancy List 
    [OperationContract]
    [WebGet(UriTemplate = "/discrepancyList/?EmpId={empId}", ResponseFormat = WebMessageFormat.Json)]
    List<DiscrepancyList> discrepancyList(String empId);


    // Discrepancy Details 
    [OperationContract]
    [WebGet(UriTemplate = "/discrepancyDetails/?Voucher={voucher}&empId={empId}", ResponseFormat = WebMessageFormat.Json)]
    List<DiscrepancyDetails> discrepancyDetails(String voucher, String empId);

    // Approve/Reject Discrepancy Details 
    [OperationContract]
    [WebInvoke(UriTemplate = "/discrepancyApprove", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean discrepancyApprove(List<DiscrepancyDetails> discrepancyDetails);

    // Raise Discrepancy Details 
    [OperationContract]
    [WebInvoke(UriTemplate = "/raiseDiscrepancy", Method = "POST",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    Boolean raiseDiscrepancy(RaiseDiscrepancy discrepancyDetails);

    // Purchase Order List
    [OperationContract]
    [WebGet(UriTemplate = "/purchaseOrder", ResponseFormat = WebMessageFormat.Json)]
    List<PurchaseOrder> purchaseOrder();


    // Purchase Order Details 
    [OperationContract]
    [WebGet(UriTemplate = "/purchaseDetails/?POId={poId}",
        RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
    List<PurchaseOrderDetails> purchaseDetails(int poId);

    // Approve Purchase Order  
    [OperationContract]
    [WebGet(UriTemplate = "/approvePurchase/?POId={poId}", ResponseFormat = WebMessageFormat.Json)]
    bool approvePurchase(int poId);
}




//Delegate
[DataContract]
public class Delegate
{
    public Delegate(LoginDetails loginDetails, string startDate, string endDate, List<LoginDetails> empList)
    {
        LoginDetails = loginDetails;
        StartDate = startDate;
        EndDate = endDate;
        EmpList = empList;
    }
    [DataMember]
    public LoginDetails LoginDetails { get; set; }
    [DataMember]
    public string StartDate { get; set; }
    [DataMember]
    public string EndDate { get; set; }
    [DataMember]
    public List<LoginDetails> EmpList { get; set; }
}

//Collection Point and Representative
[DataContract]
public class DeptDetails
{
    public DeptDetails(LoginDetails prevRep, String prevLocation, string location, LoginDetails newRep, List<CollectionPoints> collectionPoint, List<LoginDetails> employees)
    {
        PrevRep = prevRep;
        PrevLocation = prevLocation;
        Location = location;
        NewRep = newRep;
        CollectionPoint = collectionPoint;
        Employees = employees;
    }

    public DeptDetails(string prevLocation, LoginDetails prevRep, List<LoginDetails> employees, List<CollectionPoints> collectionPoint)
    {
        PrevLocation = prevLocation;
        PrevRep = prevRep;
        Employees = employees;
        CollectionPoint = collectionPoint;
    }

    [DataMember]
    public string PrevLocation { get; set; }
    [DataMember]
    public string Location { get; set; }
    [DataMember]
    public LoginDetails NewRep { get; set; }
    [DataMember]
    public LoginDetails PrevRep { get; set; }
    [DataMember]
    public List<LoginDetails> Employees { get; set; }
    [DataMember]
    public List<CollectionPoints> CollectionPoint { get; set; }
}

//collection point
[DataContract]
public class CollectionPoints
{
    public CollectionPoints(string location, int id, string description)
    {
        Location = location;
        Id = id;
        Description = description;
    }

    [DataMember]
    public string Location { get; set; }
    [DataMember]
    public int Id { get; set; }
    [DataMember]
    public string Description { get; set; }
}

// Data Contract for Login or user details.
[DataContract]
public class LoginDetails
{
    public LoginDetails(string empId, string password, string employeeName, string role, bool responseStatus, string deptCode, string department, string additionalRole)
    {
        EmpId = empId;
        Password = password;
        EmployeeName = employeeName;
        Role = role;
        ResponseStatus = responseStatus;
        DeptCode = deptCode;
        Department = department;
        AdditionalRole = additionalRole;
    }

    public LoginDetails(string empId, string password)
    {
        EmpId = empId;
        Password = password;
    }

    [DataMember]
    public string EmpId { get; set; }

    [DataMember]
    public string Password { get; set; }

    [DataMember]
    public string EmployeeName { get; set; }

    [DataMember]
    public string Role { get; set; }

    [DataMember]
    public bool ResponseStatus { get; set; }

    [DataMember]
    public string DeptCode { get; set; }

    [DataMember]
    public string Department { get; set; }

    [DataMember]
    public string AdditionalRole { get; set; }



}

// Retrieval list
[DataContract]
public class Retrieval_List
{
    public Retrieval_List(bool responseStatus, List<RetrievalList> data)
    {
        ResponseStatus = responseStatus;
        Data = data;
    }

    [DataMember]
    public bool ResponseStatus { get; set; }


    [DataMember]
    public List<RetrievalList> Data { get; set; }
}

//List of Retrieval list
[DataContract]
public class RetrievalList
{
    public RetrievalList(int? retrievedQty, int binId, string itemCode, string itemDescription, string unit, int? itemNeeded, int? availableQty, List<DeptBreakDown> itemByDept, Dictionary<String, DisbursementList> disbursementLists)
    {
        RetrievedQty = retrievedQty;
        BinId = binId;
        ItemCode = itemCode;
        ItemDescription = itemDescription;
        Unit = unit;
        ItemNeeded = itemNeeded;
        AvailableQty = availableQty;
        ItemByDept = itemByDept;
        DisbursementLists = disbursementLists;
    }

    [DataMember]
    public int? RetrievedQty { get; set; }

    [DataMember]
    public int BinId { get; set; }

    [DataMember]
    public string ItemCode { get; set; }

    [DataMember]
    public string ItemDescription { get; set; }

    [DataMember]
    public string Unit { get; set; }

    [DataMember]
    public int? ItemNeeded { get; set; }

    [DataMember]
    public int? AvailableQty { get; set; }

    [DataMember]
    public List<DeptBreakDown> ItemByDept { get; set; }

    [DataMember]
    public Dictionary<string, DisbursementList> DisbursementLists { get; set; }
}

//Dept BreakDown
[DataContract]
public class DeptBreakDown
{
    public DeptBreakDown(string deptCode, string deptDescription, int? itemRequested, int? itemRetrieved, int retrievalId)
    {
        DeptCode = deptCode;
        DeptDescription = deptDescription;
        ItemRequested = itemRequested;
        ItemRetrieved = itemRetrieved;
        RetrievalId = retrievalId;
    }

    [DataMember]
    public string DeptCode { get; set; }

    [DataMember]
    public string DeptDescription { get; set; }

    [DataMember]
    public int? ItemRequested { get; set; }

    [DataMember]
    public int? ItemRetrieved { get; set; }

    [DataMember]
    public int RetrievalId { get; set; }
}

//Disbursement List
[DataContract]
public class DisbursementList
{
    public DisbursementList(int? cPoint, String location, String disbDate, String deptName, String empId, string status, List<DisbursementDetails> disbursement)
    {
        CPoint = cPoint;
        DisbDate = disbDate;
        EmpId = empId;
        Status = status;
        DeptName = deptName;
        DisbDetails = disbursement;
        Location = location;

    }
    [DataMember]
    public int? CPoint { get; set; }
    [DataMember]
    public String Location { get; set; }
    [DataMember]
    public String DeptName { get; set; }
    [DataMember]
    public String DisbDate { get; set; }
    [DataMember]
    public String EmpId { get; set; }
    [DataMember]
    public string Status { get; set; }

    [DataMember]
    public List<DisbursementDetails> DisbDetails { get; set; }

}
[DataContract]
public class DisbursementDetails
{
    public DisbursementDetails(string category, String itemCode, int desbId, string description, string unit, int retrievedQuantity, int requestedQuantity, string status)
    {
        Category = category;
        Description = description;
        Unit = unit;
        RetrievedQuantity = retrievedQuantity;
        RequestedQuantity = requestedQuantity;
        Status = status;
        ItemCode = itemCode;
        DesbId = desbId;
    }

    [DataMember]
    public string Category { get; set; }
    [DataMember]
    public int DesbId { get; set; }
    [DataMember]
    public string ItemCode { get; set; }
    [DataMember]
    public string Description { get; set; }
    [DataMember]
    public string Unit { get; set; }
    [DataMember]
    public int RetrievedQuantity { get; set; }
    [DataMember]
    public int RequestedQuantity { get; set; }
    [DataMember]
    public string Status { get; set; }
}

// Search items
[DataContract]
public class SearchDetails
{
    public SearchDetails(bool responseStatus, List<Items> data)
    {
        ResponseStatus = responseStatus;
        Data = data;
    }

    [DataMember]
    public bool ResponseStatus { get; set; }

    [DataMember]
    public List<Items> Data { get; set; }
}

// Item Details
[DataContract]
public class Items
{
    public Items(string unit, string itemCode, string itemDescription)
    {
        Unit = unit;
        ItemCode = itemCode;
        ItemDescription = itemDescription;
    }

    [DataMember]
    public string Unit { get; set; }

    [DataMember]
    public string ItemCode { get; set; }

    [DataMember]
    public string ItemDescription { get; set; }

}

// list of requisition by employee
[DataContract]
public class Requisition_List
{
    public Requisition_List(List<RequisitionList> data, LoginDetails emp)
    {
        Data = data;
        Emp = emp;
    }

    [DataMember]
    public List<RequisitionList> Data { get; set; }

    [DataMember]
    public LoginDetails Emp { get; set; }
}

//View Requition List by employee
[DataContract]
public class RequisitionList
{
    public RequisitionList(string requestdate, string requestId)
    {
        Requestdate = requestdate;
        RequestId = requestId;
    }

    [DataMember]
    public string Requestdate { get; set; }

    [DataMember]
    public string RequestId { get; set; }
}


// list of requisition
[DataContract]
public class Requisition_Details
{
    public Requisition_Details(List<RequisitionDetails> data, LoginDetails emp, string reqId, string reqDate, string comment, string status, string rejComment)
    {
        Data = data;
        Emp = emp;
        ReqId = reqId;
        ReqDate = reqDate;
        Comment = comment;
        Status = status;
        RejComment = rejComment;
    }

    [DataMember]
    public List<RequisitionDetails> Data { get; set; }

    [DataMember]
    public LoginDetails Emp { get; set; }

    [DataMember]
    public string ReqId { get; set; }

    [DataMember]
    public string ReqDate { get; set; }

    [DataMember]
    public string Comment { get; set; }

    [DataMember]
    public string Status { get; set; }

    [DataMember]
    public string RejComment { get; set; }
}

// View Requition List by employee
[DataContract]
public class RequisitionDetails
{
    public RequisitionDetails(string unit, int? quantity, string category, string itemCode, string description)
    {
        Unit = unit;
        Quantity = quantity;
        Category = category;
        ItemCode = itemCode;
        Description = description;
    }

    [DataMember]
    public string Unit { get; set; }

    [DataMember]
    public int? Quantity { get; set; }

    [DataMember]
    public string Category { get; set; }

    [DataMember]
    public string ItemCode { get; set; }

    [DataMember]
    public string Description { get; set; }
}

//Disbursement approval
[DataContract]
public class AppDisbursement
{
    public AppDisbursement(LoginDetails login, DisbursementList disbDetail)
    {
        Login = login;
        DisbDetail = disbDetail;
    }
    [DataMember]
    public LoginDetails Login { get; set; }
    [DataMember]
    public DisbursementList DisbDetail { get; set; }

}

//View Discrepancy 
[DataContract]
public class DiscrepancyList
{
    public DiscrepancyList(string voucherId, string date, string raisedBy, string status)
    {
        Status = status;
        VoucherId = voucherId;
        Date = date;
        RaisedBy = raisedBy;

    }

    [DataMember]
    public string VoucherId { get; set; }

    [DataMember]
    public string Date { get; set; }
    [DataMember]
    public string RaisedBy { get; set; }
    [DataMember]
    public string Status { get; set; }
}

//View Discrepancy Details
[DataContract]
public class DiscrepancyDetails
{
    public DiscrepancyDetails(int discrepancyId, string item, string description, int? qty, string reason, string status)
    {
        Item = item;
        Description = description;
        Qty = qty;
        Reason = reason;
        Status = status;
        DiscrepancyId = discrepancyId;
    }
    [DataMember]
    public int DiscrepancyId { get; set; }
    [DataMember]
    public string Item { get; set; }
    [DataMember]
    public string Description { get; set; }
    [DataMember]
    public int? Qty { get; set; }
    [DataMember]
    public string Reason { get; set; }
    [DataMember]
    public string Status { get; set; }
}


//Raise Discrepancy
[DataContract]
public class RaiseDiscrepancy
{
    public RaiseDiscrepancy(List<DiscrepancyDetails> details, string empId)
    {
        Details = details;
        EmpId = empId;
    }
    [DataMember]
    public string EmpId { get; set; }
    [DataMember]
    public List<DiscrepancyDetails> Details { get; set; }
}
//View Purchase Order
[DataContract]
public class PurchaseOrder
{
    public PurchaseOrder(int orderId, string expDate, string status)
    {
        OrderId = orderId;
        ExpDate = expDate;
        Status = status;
    }
    [DataMember]
    public int OrderId { get; set; }
    [DataMember]
    public string ExpDate { get; set; }
    [DataMember]
    public string Status { get; set; }
}


//view Purchase Order Details
[DataContract]
public class PurchaseOrderDetails
{
    public PurchaseOrderDetails(string supplier, string orderDate, string item, string description, int minQty, int ordQty)
    {
        Supplier = supplier;
        OrderDate = orderDate;
        Item = item;
        Description = description;
        MinQty = minQty;
        OrdQty = ordQty;
    }
    [DataMember]
    public string Supplier { get; set; }
    [DataMember]
    public string OrderDate { get; set; }
    [DataMember]
    public string Item { get; set; }
    [DataMember]
    public string Description { get; set; }
    [DataMember]
    public int MinQty { get; set; }
    [DataMember]
    public int OrdQty { get; set; }
}
