using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;

namespace LUSIS_CONTROLLER.Controller
{
    public class DiscrepancyController
    {
        MainController mc = new MainController();
        EF_Facade facade;

        public DiscrepancyController()
        {
            facade = new EF_Facade();
        }

        // Get DiscrepancyVoucherList (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getDiscrepancyVoucherList()
        {
            return facade.getDiscrepancyVoucherList();
        }

        // Get ApproveDiscrepancyVoucherList (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getAppDisVoucherList(String appID)
        {
            return facade.getAppDisVoucherList(appID);
        }

        // Get DiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getDiscrepancyVoucherItems(String vouID)
        {
            return facade.getDiscrepancyVoucherItems(vouID);
        }

        // Get PendingDiscrepancyVoucherItems ByVoucherID (Return List<Discrepancy> or Null) // Benny
        public List<Discrepancy> getPendDisVoucherItems(String vouID)
        {
            return facade.getPendDisVoucherItems(vouID);
        }

        // Set DiscrepancyList (Return True or False) // Benny
        // Used to update status of a list of Discrepancy.
        public bool setDiscrepancyList(List<Discrepancy> disList)
        {
            return facade.setDiscrepancyList(disList);
        }

        // Set Discrepancy (Return True or False) // Benny
        // Used to update status of a Discrepancy.
        public bool setDiscrepancy(Discrepancy dis)
        {
            return facade.setDiscrepancy(dis);
        }

        public int addDiscrepancy(List<Discrepancy> listDiscripancy, string empID)
        {
            String newVoucherId_supervisor = getNewDescripancyVoucherId();

            String newVoucherId_manager = newVoucherId_supervisor.Split('/').First();
            newVoucherId_manager = newVoucherId_manager.Substring(1);
            long i = Convert.ToInt32(newVoucherId_manager) + 1;
            newVoucherId_manager = "D" + i + "/" + DateTime.Now.Year;
            int rowAffected = 0;


            string fromemail = mc.selectEmployeeByID(empID).Email;
            string toEmail = mc.getSupervisor().Email;
            string toManager = mc.getManager().Email;
            string message = "Hi Sir, <br> I have raised some discrepencies. Could you please check and approve the same? <br><br> <b><a href='http://localhost:5000/Views/storeAppVoucher.aspx'>View Discrepency List</a></a>";
            if (mc.sendEmail(fromemail, toEmail, "Purchase Orders Placed", message))


            foreach (Discrepancy disc in listDiscripancy)
            {
                String roll = "";
                Item item = new Item();
                item = facade.getItembyId(disc.ItemCode);
                Employee emp = getApprover(item, disc.Quantity, ref roll);

                if (roll == "Manager")
                {
                    disc.VoucherID = newVoucherId_manager;
                    mc.sendEmail(fromemail, toManager, "Purchase Orders Placed", message);
                }
                else
                {
                    disc.VoucherID = newVoucherId_supervisor;
                    mc.sendEmail(fromemail, toEmail, "Purchase Orders Placed", message);
                }
                disc.ApprovedBy = emp.EmpID;
                DateTime? raisedDate = DateTime.Now;
                disc.Date = raisedDate.Value;
                disc.Status = "Pending";

                rowAffected += facade.addDiscrepancy(disc);
            }
            return rowAffected;
        }

        public String getNewDescripancyVoucherId()
        {
            int lastDiscId = facade.getRunningVoucherID();

            int i = Convert.ToInt32(lastDiscId) + 1;
            String newDiscId = "D" + i + "/" + DateTime.Now.Year;
            return newDiscId;
        }

        public double? getItemPrice(Item item)
        {
            double? unitPrice = 10000000;
            List<ItemSupplierDetail> listItemSupDtls = item.ItemSupplierDetails.ToList();
            foreach (ItemSupplierDetail dtls in listItemSupDtls)
            {
                if (unitPrice > dtls.UnitPrice)
                {
                    unitPrice = dtls.UnitPrice;
                }
            }
            return unitPrice;
        }

        public Employee getApprover(Item item, int? qty, ref String roll)
        {
            double? unitPrice = getItemPrice(item);
            double? totalPrice = unitPrice * qty;
            if (totalPrice > 250 || totalPrice < -250)
            {
                roll = "Manager";
                return facade.getStoreManager();
            }
            else
            {
                roll = "Supervisor";
                return facade.getStoreSupervisor();
            }
        }
        public int getPendingDiscrepancy()
        {
            List<Discrepancy> listPendingDisc = facade.getPendingDiscrepancy();
            if (listPendingDisc != null)
            {
                return listPendingDisc.Count();
            }
            return 0;
        }
    }
}
