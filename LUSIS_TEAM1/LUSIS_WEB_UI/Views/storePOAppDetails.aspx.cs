using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storePOAppDetails : System.Web.UI.Page
    {
        MainController mc;
        string pod;
        string empID = "";
        Employee emp;
        PurchaseOrder pList;
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            mc = new MainController();
            pod = Request.QueryString["purchaseID"];
            lblOrder.Text = pod;
            getPODetails();
            emp = (Employee)Session["Logon"];
            empID = Session["EmpID"].ToString();
            
        }
        public void getPODetails()
        {
            var list = mc.GetPoDetails(pod);
            pList = mc.GetPoList(pod);

            if (pList.Status != "Pending" )
            {
                Label1.Visible = true;
                Label1.Text = "<img src='../Signature/" + empID + ".png'>";
                btnApprove.Visible = false;
            }
            else
            {
                Label1.Visible = false;
                btnApprove.Visible = true;
            }


            supName.Text = mc.getSupName(pList.SupplierID);
            lblOrderDate.Text = pList.OrderedDate.Value.ToString("dd-MMM-yyyy");

            foreach (var p in list)
            {
                var r = new TableRow();
                var c1 = new TableCell();
                c1.Text = p.ItemCode;
                r.Cells.Add(c1);

                var c2 = new TableCell();
                c2.Text = mc.getItemDesc(p.ItemCode);
                r.Cells.Add(c2);

                var c3=new TableCell();
                c3.Text = (mc.getItemMinOrder(p.ItemCode)).ToString();
                r.Cells.Add(c3);

                var c4 = new TableCell();
                c4.Text = p.Quantity.ToString();
                r.Cells.Add(c4);


                TableRaiseReq.Rows.Add(r);
            }
        }

        protected void btnApprove_Click(object sender, EventArgs e)
        {
            var pOD = Convert.ToInt32(pod);
            mc.UpdatePoDetail(pOD);
            btnApprove.Visible = false;
            Label1.Text = "<img src='../Signature/" + empID + ".png'>";
            
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
                        authorization = false;
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