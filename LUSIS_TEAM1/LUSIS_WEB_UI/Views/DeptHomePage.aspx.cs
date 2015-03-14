using System;
using System.Collections.Generic;
using LUSIS_EF_FACADE;
using LUSIS_CONTROLLER.Controller;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptHomePage : System.Web.UI.Page
    {
        readonly MainController maincontroller = new MainController();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();

            var deptCode = Session["DeptCode"].ToString();
            var role = Session["Role"].ToString();

            if (role == "DeptHead" || role == "Delegate")
            {
                var disbList = maincontroller.getReadyDisbursements(deptCode);
                if (disbList != null)
                {
                    LblCont1.Text = "ID: " + disbList.Id.ToString();
                }
                else
                    LblCont1.Text = "NO";
                LinkCont1.PostBackUrl = "DeptDisbursementLists.aspx";

                var reqList = maincontroller.getDeptAppRequisitionList(deptCode);
                LblCont2.Text = reqList.Count.ToString();
                LinkCont2.PostBackUrl = "DeptAppRequisitionList.aspx";
            }
            else if (role == "Representative")

            {
                var disbList = maincontroller.getReadyDisbursements(deptCode);
                if (disbList != null)
                {
                    LblCont1.Text = "ID: " + disbList.Id.ToString();
                }
                else
                    LblCont1.Text = "NO";
                LinkCont1.PostBackUrl = "DeptDisbursementLists.aspx";

                LblCont2Text.Text = "Approved Requisitions";
                var reqList1 = maincontroller.getDeptRequisitionList(deptCode);
                int count = 0;
                foreach (var r in reqList1)
                {
                    if (r.ApprovalStatus == "Approved" && (r.Status == "New" || r.Status == "Retrieval"))
                    {
                        count++;
                    }
                }
                LblCont2.Text = count.ToString();
                LinkCont2.PostBackUrl = "DeptRequisitionList.aspx";
            }
            else
            {
                LblCont1Text.Text = "Approved Requisitions";
                var reqList1 = maincontroller.getDeptRequisitionList(deptCode);
                int count = 0;
                foreach (var r in reqList1)
                {
                    if (r.ApprovalStatus == "Approved" && (r.Status == "New" || r.Status == "Retrieval"))
                    {
                        count++;
                    }
                }
                LblCont1.Text = count.ToString();
                LinkCont1.PostBackUrl = "DeptRequisitionList.aspx";

                var reqList2 = maincontroller.getDeptAppRequisitionList(deptCode);
                LblCont2.Text = reqList2.Count.ToString();
                LinkCont2.PostBackUrl = "DeptRequisitionList.aspx";
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
                        authorization = true;
                        break;
                    case "Delegate":
                        authorization = true;
                        break;
                    case "Representative":
                        authorization = true;
                        break;
                    case "Employee":
                        authorization = true;
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

                if (!authorization)
                {
                    Response.Redirect("~/Views/Errorpage.aspx");
                }
            }
        }
    }
}