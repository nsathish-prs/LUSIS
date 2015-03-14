using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptAppRequisitionList : System.Web.UI.Page
    {
        MainController MainCon;

        protected void Page_Load(object sender, EventArgs e)
        {
            MainCon = new MainController();
            List<Requisition> requisitionList;

            checkAuthorization();

            if (Session["DeptCode"] != null)
                requisitionList = MainCon.getDeptAppRequisitionList(Convert.ToString(Session["DeptCode"]));
            else
                requisitionList = null;

            //Fill TableAppReqList.

            if (requisitionList != null)
            {
                for (var i = 0; i < requisitionList.Count; i++)
                {
                    var row = new TableRow();
                    var reqID = new TableCell();
                    //reqID.Text = requisitionList[i].RequisitionID;

                    var lnkReq = new LinkButton();
                    //lnkReq.ID = "lnkReq" + count;
                    lnkReq.Text = requisitionList[i].RequisitionID;
                    lnkReq.Click += new EventHandler(lnkReq_Click);

                    reqID.Controls.Add(lnkReq);
                    row.Cells.Add(reqID);
                    //count++;


                    row.Cells.Add(reqID);

                    var date = new TableCell();
                    date.Text = requisitionList[i].RequestedDate.Value.ToString("dd-MMM-yyyy");
                    row.Cells.Add(date);

                    var emp = new TableCell();
                    emp.Text = requisitionList[i].Employee.Name;
                    row.Cells.Add(emp);

                    //TableCell status = new TableCell();
                    //status.Text = requisitionList[i].ApprovalStatus;
                    //row.Cells.Add(status);

                    TableAppReqList.Rows.Add(row);
                }
            }
            else
            {
                TableAppReqList.Visible = false;
            }
        }

        protected void lnkReq_Click(object sender, EventArgs e)
        {
            var control = (LinkButton)sender;
            Requisition req = null;
            req = MainCon.GetRequisitionById(control.Text);
            if (req != null)
            {
                    Response.Redirect("~/Views/DeptAppRequisition.aspx?ReqID=" + control.Text);
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