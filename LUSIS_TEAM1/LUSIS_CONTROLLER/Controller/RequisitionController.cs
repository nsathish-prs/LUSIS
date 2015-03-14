using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;

namespace LUSIS_CONTROLLER.Controller
{
    public class RequisitionController
    {
        EF_Facade facade;

        public RequisitionController()
        {
            facade = new EF_Facade();
        }

        public List<Item> getItemCatalog()
        {
            List<Item> itemCatalog = facade.getItemCatalog();
            return itemCatalog;
        }

        // Search by Item Description
        public List<Item> getSearchItemCatalog(String key)
        {
            List<Item> searchItem = facade.getSearchItemCatalog(key);
            return searchItem;
        }
        // Get DeptRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptRequisitionList(String dept)
        {
            List<Requisition> requisitionList = facade.getDeptRequisitionList(dept);
            return requisitionList;
        }

        // Get EmpRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getEmpRequisitionList(String dept)
        {
            List<Requisition> requisitionList = facade.getEmpRequisitionList(dept);
            return requisitionList;
        }

        // Get DeptAppRequisitionList (Return List<Requisition> or Null)
        public List<Requisition> getDeptAppRequisitionList(String dept)
        {
            List<Requisition> requisitionList = facade.getDeptAppRequisitionList(dept);
            return requisitionList;
        }

        // Get RequisitionByID (Return Requisition or Null)
        public Requisition getRequisitionByID(String reqID)
        {
            Requisition requisition = facade.getRequisitionByID(reqID);
            return requisition;
        }

        // Set Requisition (Return Requisition or Null)
        public Requisition setRequisition(Requisition req)
        {
            Requisition requisition = facade.setRequisition(req);
            return requisition;
        }

        // Get RequisitionDetailsByRequisitionID (Return List<RequisitionDetail> or Null)
        public List<RequisitionDetail> getReqDetailsByReqID(String reqID)
        {
            List<RequisitionDetail> reqDetails = facade.getReqDetailsByReqID(reqID);
            return reqDetails;
        }

        public Item selectItemByDescription(String description)
        {
            Item item = facade.getItemByDesc(description);
            if (item == null)
            {
                return null;
            }
            return item;
        }

        public int addNewRequisition(Requisition req)
        {
            return facade.insertNewRequisition(req);
        }

        public int addNewRequisitionDetail(RequisitionDetail reqDetail)
        {
            return facade.insertRequisitionDetail(reqDetail);
        }

        // Delete RequisitionDetailsByReqID
        public int deleteReqDetailsByReqID(String ReqID)
        {
            return facade.deleteReqDetailsByReqID(ReqID);
        }

        public Employee selectEmployeeByID(String empID)
        {
            Employee employee = facade.getEmployee(empID);
            if (employee == null)
            {
                return null;
            }
            return employee;
        }

        private int getRunningRequisitionNumber(String deptCode)
        {
            List<String> listRequisitionId = facade.getDeptRequisitionId(deptCode);
            if (!listRequisitionId.Any() || listRequisitionId == null)
            {
                return 1;
            }
            else
            {
                List<int> listRunningNum = new List<int>();
                foreach (String requisitionId in listRequisitionId)
                {
                    String[] runningNumber = requisitionId.Split('/');
                    listRunningNum.Add(Convert.ToInt32(runningNumber[2]));
                }
                listRunningNum.Sort();
                return listRunningNum.Last();
            }
        }

        // Create a new Requisition ID.
        public String getNewRequisitionId(String empId)
        {
            Employee emp = selectEmployeeByID(empId);
            String deptCode = "";
            if (emp != null)
            {
                deptCode = emp.DeptCode;
            }
            int runningNo = getRunningRequisitionNumber(deptCode) + 1;
            String requisitionId = deptCode + "/R/" + runningNo + "/" + DateTime.Now.Year;
            return requisitionId;
        }

        public Employee selectDeptHead(String deptId)
        {
            Employee emp = facade.getDeptHead(deptId);
            if (emp != null)
            {
                return emp;
            }
            return null;
        }

        // createNewRequisitionWithDetails() (Return bool) / editNewRequisitionWithDetails()
        public bool createNewRequisitionWithDetails(Employee emp, String reqID, String comments, List<RequisitionDetail> reqDetList)
        {
            // Creating a new Requisition
            Requisition newReq = new Requisition();
            Boolean flag = false;
            if (reqID == null)
            {
                newReq.RequisitionID = getNewRequisitionId(emp.EmpID);
                flag = true;
            }
            else
            {
                newReq.RequisitionID = reqID;
                deleteReqDetailsByReqID(reqID);
            }
            newReq.ApprovalStatus = "Pending";
            newReq.EmpID = emp.EmpID;
            DateTime? reqDate = DateTime.Now;
            newReq.RequestedDate = reqDate.Value;
            newReq.Status = "New";
            newReq.RequisitionComment = comments;
            if (flag)
            {
                int noOfReqInserted = addNewRequisition(newReq);
            }
            else
            {
                Requisition requisition = getRequisitionByID(reqID);
                requisition.RequisitionComment = comments;
                setRequisition(requisition);
            }
            int noOfReqItems = 0;
            foreach (RequisitionDetail reqDet in reqDetList)
            {
                reqDet.RequisitionID = newReq.RequisitionID;

                noOfReqItems += addNewRequisitionDetail(reqDet);
            }
            if (noOfReqItems == reqDetList.Count)
            {
                return true; //New Requisition and all Requisition Details inserted successfully.
            }

            return false; //New Requisition failed.
        }

        public List<Requisition> getPendingRequisitions(String DeptCode)
        {
            List<Requisition> reqList = facade.getPendingRequisitions(DeptCode);
            return reqList; 
        }
    }
}
