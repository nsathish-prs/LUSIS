using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storePOList : System.Web.UI.Page
    {
        MainController mc;
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            mc = new MainController();
            fillTable();
        }

        public void fillTable()
        {
            var list = mc.getPOdetails();
            if (list.Count == 0)
            {
                TableRaiseReq.Visible = false;
            }
            else
            {
                var role = Session["Role"].ToString();
                foreach (var p in list)
                {
                    var r = new TableRow();
                    var c1 = new TableCell();
                    var h = new HyperLink();
                    h.Text = p.PurchaseOrderNum.ToString();
                    if (role == "Clerk")
                    {
                        h.NavigateUrl = "storePODetails.aspx?purchaseID=" + h.Text;
                    }
                    else if (role == "Supervisor")
                    {
                        h.NavigateUrl = "storePOAppDetails.aspx?purchaseID=" + h.Text;
                    }
                    c1.Controls.Add(h);
                    r.Cells.Add(c1);
                    var date = "";
                    var c2 = new TableCell();
                    if (p.ExpectedDeliveryDate != null)
                    {
                        date += p.ExpectedDeliveryDate.Value.ToString("dd-MMM-yyyy");
                    }
                    else
                    {
                        date += "";
                    }
                    //DateTime? date = p.ExpectedDeliveryDate;
                    c2.Text = date;


                    //p.ExpectedDeliveryDate.ToString();
                    r.Cells.Add(c2);

                    var c3 = new TableCell();
                    if (p.Status != null)
                    { c3.Text = p.Status; }
                    else
                        c3.Text = "Pending";
                    r.Cells.Add(c3);

                    TableRaiseReq.Rows.Add(r);
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