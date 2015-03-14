using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;
namespace LUSIS_CONTROLLER.Controller
{
    public class RetrievalController
    {
        EF_Facade facade = new EF_Facade();
        
        DisburseController disbController = new DisburseController();
 
        public List<Object> selectRequistionByDepartment()
        {
            RetrievalController controller = new RetrievalController();
            return facade.getRequistionByDepartment();
            
        }
 
        List<RetrievalByItem> retByItem = new List<RetrievalByItem>();
        public void generateRetrieval()
        {
            RetrievalByItem pendingRetrieval = facade.getPendingRetrievalList(); 

            int retrievalId = 1;
            if (facade.getLastRetrieval() != null)
            {
                retrievalId = facade.getLastRetrieval().RetrievalID + 1; 
            }
            if (pendingRetrieval != null)
            {
                retrievalId = pendingRetrieval.RetrievalID; //get retrievalId that is pending.

                List<RetrievalByItem> ret = facade.getRetrievalById(retrievalId); 
                List<DisbursementItem> listDisburse = disbController.selectDisburseItem(retrievalId);
                foreach (DisbursementItem disbItem in listDisburse)
                {
                    disbController.deleteDisburseItem(disbItem);

                }
                foreach (RetrievalByItem r in ret)
                {
                    facade.deleteRetrievalList(r); 
                }

            }

            List<DisbursementItem> retByDept1 = disbController.selectRequistionByDepartmentItem1();


            List<DisbursementItem> listPartiallDisburse = disbController.selectPartiallyRetrievedDisburs();
            foreach (DisbursementItem partialDisb in listPartiallDisburse)
            {
                bool exist = false;
                foreach (DisbursementItem disb in retByDept1)
                {
                    if (disb.ItemCode == partialDisb.ItemCode && disb.DeptCode == partialDisb.DeptCode)
                    {
                        exist = true;
                        disb.RequestedQty += (partialDisb.RequestedQty - partialDisb.RetrievedQuantity);

                            break;
                    }
                }
                if (exist == false)
                {
                    //Insert new Disburse Item if not found.
                    DisbursementItem newDisburse = new DisbursementItem();
                    newDisburse.RetrievalID = retrievalId;
                    newDisburse.ItemCode = partialDisb.ItemCode;
                    newDisburse.DeptCode = partialDisb.DeptCode;
                    newDisburse.RequestedQty = partialDisb.RequestedQty;
                    newDisburse.RetrievedQuantity = partialDisb.RetrievedQuantity;
                    retByDept1.Add(newDisburse);
                    //disbController.insertDisbursementItem(newDisburse);
                }
            }
            retByItem = facade.getRequisitionForConsolidation();//Done

            //Insert retrieval by Item.
            foreach (RetrievalByItem r in retByItem)
            {
                RetrievalByItem newRetrieval = new RetrievalByItem();
                newRetrieval.RetrievalID = retrievalId;
                newRetrieval.ItemCode = r.ItemCode;
                newRetrieval.RequestedQty = 0;
                foreach (DisbursementItem disb in retByDept1)
                {
                    if (disb.ItemCode == r.ItemCode)
                    {
                        newRetrieval.RequestedQty += disb.RequestedQty;
                    }
                }
                newRetrieval.RetrievedQty = 0;//r.RetrievedQty;
                newRetrieval.Status = "Pending";

                facade.insertNewRetrieval(newRetrieval);
            }

            //Insert disbursement items.
            foreach (DisbursementItem newDisb in retByDept1)
            {
                DisbursementItem newDisburse = new DisbursementItem();
                newDisburse.RetrievalID = retrievalId;
                newDisburse.ItemCode = newDisb.ItemCode;
                newDisburse.DeptCode = newDisb.DeptCode;
                newDisburse.RequestedQty = newDisb.RequestedQty;
                newDisburse.RetrievedQuantity = newDisb.RetrievedQuantity;
                disbController.insertDisbursementItem(newDisburse);
            }
            
        }
    }
}
