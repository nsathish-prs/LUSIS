using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE.Facade;
using LUSIS_EF_FACADE;

namespace LUSIS_CONTROLLER.Controller
{
    public class PurchaseController 
    {
        EF_Facade ef;

        public PurchaseController()
        {
            ef = new EF_Facade();
        }
        public List<Item> itemList()
        {
            return ef.getItems();
        }

        public List<Item> purchaseItems()
        {
            return ef.purchaseItems();
        }

        public List<PurchaseOrder> getPOdetail()
        {
            return ef.getPOlist();
        }

        public string getSupName(string sID)
        {
            return ef.getSupName(sID);
        }

        public string getItemDesc(string iCode)
        {
            return ef.getItemDesc(iCode);
        }

        public bool  updatePODetail(int pod)
        {
            return ef.updatePODetail(pod);
        }

        public int getItemMinOrder(string iCode)
        {
            return ef.getItemMinOrder(iCode);
        }

        public PurchaseOrder getPOList(int pod)
        {
            return ef.getPOList(pod);
        }

        public List<ItemSupplierDetail> Lsupplier(string iCode)
        {
            return ef.lSupplierDetails(iCode);
        }

        public string getsupID(string sName)
        {
            return ef.getSupplierID(sName);
        }

        public void addnewPO(PurchaseOrder po)
        {
            ef.addpurchaseOrders(po);
        }

        public int getLastRecord()
        {
            return ef.getLastRecord();
        }

        public void addPODetail(PurchaseOrderDetail pod)
        {
            ef.addPODetail(pod);
        }

        public void updateItem(Item item, String iCode)
        {
            ef.updateItem(item, iCode);
        }
        public List<PurchaseOrderDetail> getPODetails(int pid)
        {
            return ef.getPODetails(pid);
        }
        public void ClosePurchaseOrder(PurchaseOrder PO)
        {
            ef.ClosePurchaseOrder(PO);
        }
        public void updatePODelivery(PurchaseOrderDetail pd)
        {
            ef.updatePODelivery(pd);
        }

        public void receiveDelivery(List<String> listItemCode,int PONum,String deliveryId)
        {
            InventoryController invController = new InventoryController();
            PurchaseOrder purOrder = ef.getPOList(PONum);
            foreach (PurchaseOrderDetail detail in purOrder.PurchaseOrderDetails)
            {
                foreach (String item in listItemCode)
                {
                    PurchaseOrderDetail pDetails = ef.getPODetails(item, PONum);
                    if (detail.ItemCode == pDetails.ItemCode)
                    {
                        DateTime? delDate = DateTime.Now;
                        pDetails.DeliveryID = deliveryId;
                        pDetails.DeliveryDate = delDate.Value;
                        ef.updatePODelivery(pDetails);      // update delivery details in purchase order details.
                        invController.AddNewTransaction(pDetails.ItemCode, deliveryId, PONum, pDetails.Quantity, "OrderDelivery");
                        invController.UpdateInventory(pDetails.ItemCode, "+" + pDetails.Quantity.ToString());//in the inventory  new quantity = current quantity + qty
                    }
                }
            }
            PurchaseOrder pOrder = ef.getPOList(PONum);
            bool exists = false;
            foreach (PurchaseOrderDetail pDetails in pOrder.PurchaseOrderDetails)
            {
                if (pDetails.DeliveryID == "NULL" || pDetails.DeliveryID == "")
                {
                    exists = true;
                    break;
                }
            }
            if (exists == false)
            {
                ef.ClosePurchaseOrder(pOrder);
            }
        }
        public Employee getEmployeebyRole()
        {
            return ef.getEmployeebyRole();
        }
        public Employee getManager()
        {
            return ef.getManager();
        }

    }

}
