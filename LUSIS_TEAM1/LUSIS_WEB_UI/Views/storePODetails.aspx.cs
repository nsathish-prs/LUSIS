using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storePODetails : System.Web.UI.Page
    {
        MainController mc;
        string pod;
        protected void Page_Load(object sender, EventArgs e)
        {
            txtDeliveryID.Visible = false;
            mc = new MainController();
            pod = Request.QueryString["purchaseID"];
            lblOrder.Text = pod;
            getPODetails();
        }
        public void getPODetails()
        {
            var list = mc.GetPoDetails(pod);
            var pList = mc.GetPoList(pod);
            supName.Text = mc.getSupName(pList.SupplierID);
            var status = pList.Status;
            lblOrderDate.Text = pList.OrderedDate.Value.ToString("dd-MMM-yyyy");
            submitBtn.Visible = false;

                            //<asp:TableHeaderCell>Receive Status</asp:TableHeaderCell>

                var r1=new TableHeaderRow();
                var h1=new TableHeaderCell();
                h1.Text = "Item ID"; r1.Cells.Add(h1);
                var h2=new TableHeaderCell();
                h2.Text = "Item Description"; r1.Cells.Add(h2);
                var h3=new TableHeaderCell();
                h3.Text = "Quantity"; r1.Cells.Add(h3);
                TableRaiseReq.Rows.Add(r1);
                if (status == "Approved")
                {
                    var h4 = new TableHeaderCell();
                    h4.Text = "Receive Status"; r1.Cells.Add(h4);
                    submitBtn.Visible = true;
                    txtDeliveryID.Visible = true;

                }



                var count = 1;
            foreach (var p in list)
            {
                var r = new TableRow();
                var c1 = new TableCell();
                c1.Text = p.ItemCode;
                r.Cells.Add(c1);

                var c2 = new TableCell();
                c2.Text = mc.getItemDesc(p.ItemCode);
                r.Cells.Add(c2);

                var c3 = new TableCell();
                c3.Text = p.Quantity.ToString();
                r.Cells.Add(c3);

                if (status == "Approved")
                {
                    var c4 = new TableCell();
                    var c = new CheckBox();
                    c.ID = "Status" + count;
                    c4.Controls.Add(c);
                    r.Cells.Add(c4);
                    count++;
                }

                TableRaiseReq.Rows.Add(r);
            }
        }

        protected void submitBtn_Click(object sender, EventArgs e)
        {
            //Get the id from the user.
            var deliveryID = txtDeliveryID.Text;// prompt("What is the answer to life, the universe and everything?");
            if (deliveryID != "")
            {
                var pList = mc.GetPoList(pod);
                var itemCode = "";
                var listItemCode = new List<String>();
                for (var i = 1; i < TableRaiseReq.Rows.Count; i++)
                {
                    var cbStatus = (CheckBox)TableRaiseReq.Rows[i].Cells[3].FindControl("Status" + i);
                    if (cbStatus != null && cbStatus.Checked == true)
                    {
                        itemCode = TableRaiseReq.Rows[i].Cells[0].Text;
                        var description = TableRaiseReq.Rows[i].Cells[1].Text;
                        var qty = TableRaiseReq.Rows[i].Cells[2].Text;
                        foreach (var pDetails in pList.PurchaseOrderDetails)
                        {
                            if (itemCode == pDetails.ItemCode)
                            {
                                listItemCode.Add(itemCode);
                            }
                        }
                    }
                }
                mc.receiveDelivery(listItemCode, Convert.ToInt32(pod), deliveryID);
                Response.Redirect("storeHomePage.aspx");
            }
            else
            {
                Response.Write("<script language='javascript'>alert('Please Select Items and Enter the Delivery ID);</script>");
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