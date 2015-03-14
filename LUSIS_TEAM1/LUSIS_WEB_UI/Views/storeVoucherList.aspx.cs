using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeVoucherList : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();
        Employee logonEmp;
        List<Discrepancy> disVouList;

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            if (Session["Logon"] != null)
            {
                logonEmp = (Employee)Session["Logon"];
                if (Convert.ToString(Session["Role"]) == "Manager" || Convert.ToString(Session["Role"]) == "Supervisor" || Convert.ToString(Session["Role"]) == "Clerk")
                    disVouList = MainCont.getDiscrepancyVoucherList();
            }
            else
                disVouList = null;

            //Fill TableVouList.

            if (disVouList != null)
            {
                for (var i = 0; i < disVouList.Count; i++)
                {
                    var row = new TableRow();
                    var vouID = new TableCell();

                    var lnkReq = new LinkButton();
                    lnkReq.Text = disVouList[i].VoucherID;
                    lnkReq.Click += new EventHandler(lnkReq_Click);

                    vouID.Controls.Add(lnkReq);
                    row.Cells.Add(vouID);

                    var date = new TableCell();
                    date.Text = disVouList[i].Date.Value.ToString("dd-MMM-yyyy");
                    row.Cells.Add(date);

                    var raiseBy = new TableCell();
                    raiseBy.Text = disVouList[i].Employee.Name;
                    row.Cells.Add(raiseBy);

                    var appBy = new TableCell();
                    appBy.Text = disVouList[i].EmployeeApp.Name;
                    row.Cells.Add(appBy);

                    var status = new TableCell();
                    var pendList = MainCont.getPendDisVoucherItems(disVouList[i].VoucherID);
                    if (pendList == null)
                    {
                        status.Text = "Closed";
                    }
                    else
                    {
                        if (pendList.Count() == 0)
                            status.Text = "Closed";
                        else
                            status.Text = "Pending";
                    }
                    row.Cells.Add(status);

                    TableVouList.Rows.Add(row);
                }
            }
        }

        protected void lnkReq_Click(object sender, EventArgs e)
        {
            var control = (LinkButton)sender;
            if (control.Text != "")
            {
                Response.Redirect("~/Views/storeVoucher.aspx?VouID=" + control.Text);
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