using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptRequisitionList : System.Web.UI.Page
    {
        readonly MainController MainCon = new MainController();
        Employee logonEmp;

        protected void Page_Load(object sender, EventArgs e)
        {
            TableReqList.Visible = false;
            Session["selectedItems"] = null;
            if (IsPostBack == false) 
                RadioBtnListReq.SelectedIndex = 0;
            //MainCon = new MainController();
            List<Requisition> requisitionList;
            //Employee logonEmp;

            if (Session["logon"] != null)
            {
                logonEmp = (Employee)Session["logon"];
                if (Convert.ToString(Session["Role"]) == "DeptHead" || Convert.ToString(Session["Role"]) == "Delegate")
                    RadioBtnListReq.SelectedIndex = 1;

                if (RadioBtnListReq.SelectedIndex == 0)
                {
                    requisitionList = MainCon.getEmpRequisitionList(logonEmp.EmpID);
                    LblHeadTitle.Text = "Requisition List [My]";
                }
                else
                {
                    requisitionList = MainCon.getDeptRequisitionList(logonEmp.DeptCode);
                    LblHeadTitle.Text = "Requisition List [All]";
                }
            }
            else
                requisitionList = MainCon.getEmpRequisitionList("");

            //Fill TableReqList.

            if (requisitionList.Count > 0)
            {
                for (var i = 0; i < requisitionList.Count; i++)
                {
                    var row = new TableRow();
                    var reqID = new TableCell();
                    //reqID.Text = requisitionList[i].RequisitionID;

                    var lnkReq = new LinkButton();
                    lnkReq.Text = requisitionList[i].RequisitionID;
                    lnkReq.Click += new EventHandler(lnkReq_Click);

                    reqID.Controls.Add(lnkReq);
                    row.Cells.Add(reqID);

                    var date = new TableCell();
                    date.Text = requisitionList[i].RequestedDate.Value.ToString("dd-MMM-yyyy");
                    row.Cells.Add(date);

                    var emp = new TableCell();
                    emp.Text = requisitionList[i].Employee.Name;
                    row.Cells.Add(emp);

                    var status = new TableCell();
                    status.Text = requisitionList[i].ApprovalStatus;
                    row.Cells.Add(status);

                    TableReqList.Rows.Add(row);
                }
                TableReqList.Visible = true;
            }
            else
            {
                TableReqList.Visible = false;
            }
        }

        protected void lnkReq_Click(object sender, EventArgs e)
        {
            var control = (LinkButton)sender;
            Requisition req = null;
            req = MainCon.GetRequisitionById(control.Text);
            if (req != null)
            {
                if (req.EmpID == logonEmp.EmpID && req.ApprovalStatus == "Pending")
                    Response.Redirect("~/Views/DeptNewRequisition.aspx?ReqID=" + control.Text);
                else
                    Response.Redirect("~/Views/DeptViewRequisition.aspx?ReqID=" + control.Text);
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
            }

            if (!authorization)
            {
                Response.Redirect("~/Views/Errorpage.aspx");
            }
        }
    }
}