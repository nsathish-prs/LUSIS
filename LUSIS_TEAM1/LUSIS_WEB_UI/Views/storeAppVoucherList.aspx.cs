using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeAppVoucherList : System.Web.UI.Page
    {
        readonly MainController MainCon = new MainController();
        Employee logonEmp;
        List<Discrepancy> appDisVouList;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Logon"] != null)
            {
                logonEmp = (Employee)Session["Logon"];
                checkAuthorization();
                if (Convert.ToString(Session["Role"]) == "Manager" || Convert.ToString(Session["Role"]) == "Supervisor")
                    appDisVouList = MainCon.getAppDisVoucherList(logonEmp.EmpID);
            }
            else
                appDisVouList = null;

            //Fill TableAppVouList.

            if (appDisVouList != null)
            {
                for (var i = 0; i < appDisVouList.Count; i++)
                {
                    var row = new TableRow();
                    var vouID = new TableCell();

                    var lnkReq = new LinkButton();
                    lnkReq.Text = appDisVouList[i].VoucherID;
                    lnkReq.Click += new EventHandler(lnkReq_Click);

                    vouID.Controls.Add(lnkReq);
                    row.Cells.Add(vouID);

                    var date = new TableCell();
                    date.Text = appDisVouList[i].Date.Value.ToString("dd-MMM-yyyy");
                    row.Cells.Add(date);

                    var raiseBy = new TableCell();
                    raiseBy.Text = appDisVouList[i].Employee.Name;
                    row.Cells.Add(raiseBy);

                    //TableCell appBy = new TableCell();
                    //appBy.Text = disVouAppList[i].EmployeeApp.Name;
                    //row.Cells.Add(appBy);

                    TableAppVouList.Rows.Add(row);
                }
            }
        }

        protected void lnkReq_Click(object sender, EventArgs e)
        {
            var control = (LinkButton)sender;
            if (control.Text != "")
            {
                Response.Redirect("~/Views/storeAppVoucher.aspx?VouID=" + control.Text);
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