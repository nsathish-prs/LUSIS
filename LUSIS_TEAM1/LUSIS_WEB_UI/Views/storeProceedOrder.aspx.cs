using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeProceedOrder : System.Web.UI.Page
    {
        readonly PurchaseController pc = new PurchaseController();
        readonly MainController mc = new MainController();
        string empID = null;
        List<Item> list;
        protected void Page_Load(object sender, EventArgs e)
        {
            empID = Session["EmpID"].ToString();
            checkAuthorization();
            purchaseItems();
        }

        public void purchaseItems()
        {
            list= pc.purchaseItems();
            
            foreach (var o in list)
            {
                var r = new TableRow();
                var c1 = new TableCell();
                c1.Text = o.ItemCode;
                r.Cells.Add(c1);

                var c2 = new TableCell();
                c2.Text = o.Description;
                r.Cells.Add(c2);

                var c3 = new TableCell();
                c3.Text = o.ReOrderQuantity.ToString();
                r.Cells.Add(c3);

                var c4 = new TableCell();
                var tb=new TextBox();
                //tb.ID = "requiredQty";
                c4.Controls.Add(tb);
                r.Cells.Add(c4);

                var c5 = new TableCell();
                var dr = new DropDownList();
                dr.ID = "SupplierID" + o.ItemCode;
                

                var list1 = new List<String>();
                foreach (var itemDtls in pc.Lsupplier(o.ItemCode))
                {
                    list1.Add(itemDtls.Supplier.Name);
                }
                dr.DataSource = list1;
                dr.DataBind();
                c5.Controls.Add(dr);
                r.Cells.Add(c5);

                proceedOrder.Rows.Add(r);

            }


        }

        protected void genPOrder_Click(object sender, EventArgs e)
        {
            var req=0;
            var required = false;
            for(var i=1; i<proceedOrder.Rows.Count; i++)
            {
                var t = ((TextBox)proceedOrder.Rows[i].Cells[3].Controls[0]).Text;
                if ((((TextBox)proceedOrder.Rows[i].Cells[3].Controls[0]).Text) != "")
                {
                    try
                    {
                        req = Convert.ToInt32((((TextBox)proceedOrder.Rows[i].Cells[3].Controls[0]).Text));
                        if (req > (Convert.ToInt32(proceedOrder.Rows[i].Cells[2].Text)))
                        {
                            required = true;
                        }
                        else
                            Response.Write("<script type='text/javascript'>alert('Ordering amount must be greater than Minimum Order Quantity');</script>");
                    }
                    catch (Exception)
                    {
                        Response.Write("<script type='text/javascript'>alert('Please enter the proper value for the Items');</script>");
                    }
                   
                }
            }

            if (required)
            {
                var selectedSuppliers = new List<string>();
                for (var i = 1; i < proceedOrder.Rows.Count; i++)
                {
                    selectedSuppliers.Add((((DropDownList)proceedOrder.Rows[i].Cells[4].Controls[0]).SelectedItem).ToString());
                }

                var suppliersSelected = selectedSuppliers.Distinct().ToList();

                Session["selectedSuppliers"] = suppliersSelected;

                PurchaseOrder po;
                DateTime? reqDate = DateTime.Now;
                DateTime? expDate = DateTime.Now.AddDays(5);
                PurchaseOrderDetail pod;
                Item item;
                var c = 0;

                foreach (var m in suppliersSelected)
                {
                    item = new Item();
                    po = new PurchaseOrder();
                    po.PurchaseOrderNum = mc.getLastRecord() + 1;
                    po.OrderedDate = reqDate;
                    po.SupplierID = mc.GetsupId(m);
                    po.ExpectedDeliveryDate = expDate;
                    po.Status = "Pending";
                    mc.addPurchaseOrder(po);
                    c = c + 1;
                    for (var i = 1; i < proceedOrder.Rows.Count; i++)
                    {
                        if ((((DropDownList)proceedOrder.Rows[i].Cells[4].Controls[0]).SelectedItem).ToString() == m)
                        {
                            pod = new PurchaseOrderDetail();
                            pod.ItemCode = proceedOrder.Rows[i].Cells[0].Text;
                            pod.PurchaseOrderNum = po.PurchaseOrderNum;
                            pod.Quantity = Convert.ToInt32(((TextBox)(proceedOrder.Rows[i].Cells[3].Controls[0])).Text);
                            item.Status = "Ordered";
                            var iCode = pod.ItemCode;
                            mc.addPurchaseOrderDetails(pod);
                            mc.updateItem(item, iCode);
                        }
                    }
                }
                var fromemail = mc.SelectEmployeeById(empID).Email;
                var toEmail = mc.getSupervisor().Email;
                var message = "Hi Sir, <br> I have placed some purchase orders. Could you please check and approve the same? <br><br> <b><a href='http://localhost:5000/Views/storePOList.aspx'>View Purchase Order</a></a>";
                if (mc.sendEmail(fromemail, toEmail , "Purchase Orders Placed", message))
                {
                    Response.Write("<script type='text/javascript'>alert('Purchase Order Successfully Placed');</script>");
                    Response.Redirect("storePOList.aspx");
                }
                else
                {
                    Response.Write("<script type='text/javascript'>alert('Purchase Order Successfully Placed');</script>");
                }
            }
        }

        private void checkAuthorization()
        {

            if (Session["Role"] == null)
                Response.Redirect("~/Login.aspx");

            var authorization = false;

            if (Session["Role"] != null)
            {
                switch (Convert.ToString(Session["Role"]))
                {
                    case "DeptHead":
                        authorization = false;
                        break;
                    case "Delegate":
                        authorization = false;
                        break;
                    case "Representative":
                        authorization = false;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = true;
                        break;
                    case "Supervisor":
                        authorization = true;
                        break;
                    case "Clerk":
                        authorization = true;
                        break;
                }
            }

            if (!authorization)
            {
                Response.Redirect("~/Views/Errorpage.aspx");
            }
        }

    }
}