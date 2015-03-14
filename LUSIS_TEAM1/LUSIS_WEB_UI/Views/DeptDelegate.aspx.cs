using System;
using System.Collections.Generic;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptDelegate : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();
        string deptCode = null;
        string empID=null;
        string empName = null;
        LUSIS_EF_FACADE.Delegate dele;
        protected void Page_Load(object sender, EventArgs e)
        {
            empName = Session["Name"].ToString();
            if (Session["Logon"] == null)
                Response.Redirect("~/Login.aspx");
            else
            {
                checkAuthorization();
                deptCode = Session["DeptCode"].ToString();
                fillpage();
            }
        }

        public void fillpage()
        {
            //if (Request.Params["E"]!=null)
            //    LblErrorText.Text = "Error :: Date is in incorrect format !!";
            empID = Session["EmpID"].ToString();
            deptCode = Session["DeptCode"].ToString();
            var delEmp = MainCont.getDelegateEmp(deptCode);

            if (delEmp != null)
                LblCurrDelegate.Text = delEmp.Name;
            else
                LblCurrDelegate.Text = "None";

            dele = MainCont.getDelegate(deptCode);
            if (dele != null)
            {
                if (dele.StartDate != null)
                    LblCurrStartDate.Text = dele.StartDate.Value.ToString("dd-MMM-yyyy");
                else
                    LblCurrStartDate.Text = "None";

                if (dele.EndDate != null)
                    LblCurrEndDate.Text = dele.EndDate.Value.ToString("dd-MMM-yyyy");
                else
                    LblCurrEndDate.Text = "None";
            }

            var deptEmpList = MainCont.getDeptEmployees(deptCode, "Delegate");

            foreach (var e in deptEmpList)
            {
                DDLEmpList.Items.Add(e.Name);
            }
        }

        protected void setDelegate_Click(object sender, EventArgs e)
        {
            var delName = DDLEmpList.SelectedItem.Text;
            var startDate = TxtStartDate.Text;
            var endDate = TxtEndDate.Text;
            var startDATE = new DateTime();
            var endDATE = new DateTime();
            int day;
            int month;
            int year;

            try
            {
                if (startDate != "")
                {
                    day = Convert.ToInt32(startDate.Substring(0, 2));
                    month = Convert.ToInt32(startDate.Substring(3, 2));
                    year = Convert.ToInt32(startDate.Substring(6, 4));
                    startDATE = new DateTime(year, month, day);
                    startDate = Convert.ToString(startDATE);
                }
                else
                    startDate = Convert.ToString(DateTime.Now);

                if (endDate != "")
                {
                    day = Convert.ToInt32(endDate.Substring(0, 2));
                    month = Convert.ToInt32(endDate.Substring(3, 2));
                    year = Convert.ToInt32(endDate.Substring(6, 4));
                    endDATE = new DateTime(year, month, day);
                    endDate = Convert.ToString(endDATE);

                    if (endDATE < startDATE)
                    {
                        //endDate = null; //Convert.ToString(startDATE.AddDays(15));
                        Response.Write("<script language='javascript'>alert('Error :: End Date < Start Date !!');</script>");
                    }
                    else
                    {
                        if (MainCont.updateDelegate(delName, deptCode, startDate, endDate))
                        {
                            var fromEmail = MainCont.getEmployeeByNameAndDept(empName, deptCode).Email.ToString();
                            var toEmail = MainCont.getEmployeeByNameAndDept(delName, deptCode).Email.ToString();
                            var message = "You have been assigned as Delegate from " + startDATE + "to " + endDate + ".<br> Please Take care of the requisitions raised by the other Employees.";
                            if (MainCont.sendEmail(fromEmail, toEmail, "Delegate Assigned", message))
                            {
                                Response.Write("<script language='javascript'>alert('Delegate has been assigned successfully !!');</script>");
                            }
                        }
                    }
                }
                else
                    Response.Write("<script language='javascript'>alert('Error :: Delegate failed, end date not set !!');</script>");
            }
            catch (Exception ex)
            {
                Response.Write("<script language='javascript'>alert('Error :: Delegate failed, plz check date format !!');</script>");
                //Response.Write("<script language='javascript'>window.location.href = 'DeptDelegate.aspx';</script>");
            }
            
            Response.Write("<script language='javascript'>window.location.href = 'DeptDelegate.aspx';</script>");
        }

        protected void cleDelegate_Click(object sender, EventArgs e)
        {
            var del = MainCont.getDelegateEmp(deptCode);
            if (del != null)
            {
                if (MainCont.clearDelegate(deptCode))
                {
                    var fromEmail = MainCont.getEmployeeByNameAndDept(empName, deptCode).Email.ToString();
                    var toEmail = dele.Employee.Email;
                    var message = "Thanks for assisting me by maintaining the requisitions from the Employees for these days, <b>Friend</b.>. I've returned and will take over. =) ";
                    if (MainCont.sendEmail(fromEmail, toEmail, "Thank You Friend!!", message))
                    {
                        Response.Write("<script language='javascript'>alert('Delegate has been cleared successfully !!');</script>");
                    }
                }
            }
            //Response.Redirect("~/Views/DeptDelegate.aspx");
            Response.Write("<script language='javascript'>window.location.href = 'DeptDelegate.aspx';</script>");
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
                        authorization = true;
                        break;
                    case "Delegate":
                        authorization = true;
                        break;
                    case "Representative":
                        authorization = false;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = false;
                        break;
                    case "Supervisor":
                        authorization = false;
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