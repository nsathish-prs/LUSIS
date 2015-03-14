using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;


namespace LUSIS_CONTROLLER.Controller
{
    public class DisburseController
    {
        EF_Facade facade;

        public DisburseController()
        {
            facade = new EF_Facade();
        }

        public List<DisbursementList> getDeptDisbList()
        {
            List<DisbursementList> disbursementLists = facade.getDeptDisbList();
            return disbursementLists;

        }

        public List<DisbursementList> getDeptDisbList(String deptCode)
        {
            List<DisbursementList> disbursementLists = facade.getDeptDisbList(deptCode);
            return disbursementLists;

        }

        // receive reatrieval list by Item
        public List<RetrievalByItem> receiveRetrievalByItem()
        {
            List<RetrievalByItem> retrievalItems = facade.receiveRetrievalByItem();
            return retrievalItems;
        }

        public List<DisbursementItem> getDeptDisbItemList(int disbursementID)
        {
            List<DisbursementItem> disbursementItems = facade.getDeptDisbItemList(disbursementID);
            return disbursementItems;
        }
        public List<DisbursementItem> selectDisburseItem(int retId)
        {
            return facade.getDisburseItem(retId);
        }
        public void deleteDisburseItem(DisbursementItem disbItem)
        {
            facade.deleteDisburseItem(disbItem);

        }
        public List<DisbursementItem> selectRequistionByDepartmentItem1()
        {
            return facade.getRequistionByDepartmentItem1();
        }
        public List<DisbursementItem> selectPartiallyRetrievedDisburs()
        {
            return facade.getPartiallyRetrievedDisburs();
        }
        public int insertDisbursementItem(DisbursementItem disb)
        {
            return facade.insertDisbursementItem(disb);
        }


        public Boolean generateDisb(List<DisbursementItem> disbursementItems, Dictionary<String, DisbursementList> deptDisbLists,String status)
        {
            return facade.generateDisb(disbursementItems, deptDisbLists,status);
        }
        public bool approveDisbursement(List<DisbursementItem> di, String deptCode)
        {
            
            InventoryController inventory = new InventoryController();
            foreach (DisbursementItem d in di)
            {
                if (d.RetrievedQuantity == d.RequestedQty)
                {
                    d.Status = "Retrieved";
                }
                else
                {
                    d.Status = "Partially Retrieved";
                }
                facade.approveDisbursement( di,  deptCode);

                inventory.AddNewTransaction(d.ItemCode,deptCode,(int)d.DisbursementID,d.RetrievedQuantity,"Disbursement");
                inventory.UpdateInventory(d.ItemCode, "-"+d.RetrievedQuantity.ToString());
                
            }
            return true;
        }

        public void CancelDisbursement(int selectedDisbID)
        {
            facade.CancelDisbursement(selectedDisbID);
        }




        internal DisbursementList getReadyDisbursements(String DeptCode)
        {
            DisbursementList disbList = facade.getReadyDisbursements(DeptCode);
            return disbList;
        }
    }


}

