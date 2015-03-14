using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptDisbursementLists : System.Web.UI.Page
    {
        readonly MainController mainController = new MainController();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            List<DisbursementList> list;
            var dept = Session["DeptCode"].ToString();
            if(dept.Equals("STOR"))
            list = mainController.getDeptDisbList();
            else
            {
                list = mainController.getDeptDisbList(Session["DeptCode"].ToString());
            }

            if (list.Count > 0)
            {
                foreach (var item in list)
                {
                    var tableRow = new TableRow();

                    var idCell = new TableCell();
                    var idHyperLink = new HyperLink();
                    idHyperLink.Text = item.Id.ToString();
                    idCell.Controls.Add(idHyperLink);

                    // String status = item.Status == "Ready" ? "True" : "False";
                    var status = item.Status;

                    idHyperLink.NavigateUrl = "DeptDisbursementDetails.aspx?disbursementID=" + idHyperLink.Text + "&repName=" + item.Employee.Name + "&collectionPoint=" + item.CollectionPoint1.Location + "&RepID=" + item.RepresentativeID + "&Status=" + status + "&deptName=" + item.Employee.Department.Name;

                    var dateCell = new TableCell();
                    var label1 = new Label();
                    //label.Text = DateTime.ParseExact(list[i].DisbursementDate.ToString(), "MM/dd/yy", null);
                    label1.Text = item.DisbursementDate.Value.ToString("dd-MMM-yyyy");
                    dateCell.Controls.Add(label1);

                    var deptCell = new TableCell();
                    var label4 = new Label();
                    label4.Text = item.Employee.Department.Name;
                    deptCell.Controls.Add(label4);

                    var repCell = new TableCell();
                    var label2 = new Label();
                    label2.Text = item.Employee.Name;
                    repCell.Controls.Add(label2);

                    var statusCell = new TableCell();
                    var label3 = new Label();
                    label3.Text = item.Status;
                    statusCell.Controls.Add(label3);

                    tableRow.Cells.Add(idCell);
                    tableRow.Cells.Add(dateCell);
                    tableRow.Cells.Add(deptCell);
                    tableRow.Cells.Add(repCell);
                    tableRow.Cells.Add(statusCell);

                    TableDisbList.Rows.Add(tableRow);
                }
            }
            else
            {
                TableDisbList.Visible = false;
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