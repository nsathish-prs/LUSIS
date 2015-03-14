using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;
namespace LUSIS_CONTROLLER.Controller
{
    public class InventoryController
    {
        EF_Facade facade = new EF_Facade();
        
        /*
         * for delivery order: transDesc-->DeliveryID, transType-->PurchaseOrderNum,action-->OrderDelivery
         * for discripancy: transDesc-->DescripancyID, transType-->voucherID,action-->Discrepancy
         * for disbursement: transDesc-->DeptCode, transType-->DisburseID,action-->Disbursement*/
        public void AddNewTransaction(String itemCode,String transDesc,int transType,int? qty,String action)
        {
            Transaction trans = new Transaction();
            trans.ItemCode = itemCode;
            DateTime? transDate = DateTime.Now;
            trans.TransDate = transDate.Value;
            trans.Description = transDesc;
            trans.TransQuantity = qty;
            trans.TransType = transType;
            trans.Action = action;
            trans.TransId = getNewTransID(); 
            facade.addTransaction(trans);
        }

        public String getNewTransID()
        {
            int lastTransId = facade.getRunningTransID();
            String newTransId = "";
  
            int nxtRunningNum = lastTransId + 1;
            newTransId = "TRANS" + nxtRunningNum + "/" + DateTime.Now.Year;
            return newTransId;
        }

        //Quantity, (+) for DeliveryOrder/Discrepancy, (-) for Discrepancy/Disbursement
        public int UpdateInventory(String itemCode,String Quantity)
        {
            Item item =  facade.getItembyId(itemCode);
            String type = Quantity.Substring(0,1);
            int? currentQty = 0;
            currentQty = Convert.ToInt32(Quantity);

               item.CurrentQuantity += currentQty;
           int rowsAffected = facade.UpdateInventory(itemCode, item.CurrentQuantity);
           return rowsAffected;
        }


    }
}
