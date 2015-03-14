using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeMaintainSuppliers : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            var supList = MainCont.getSupplierList();

            // Fill TableSupList

            for (var i = 0; i < supList.Count; i++)
            {
                var row = new TableRow();
                var supCode = new TableCell();

                var lnkReq = new LinkButton();
                lnkReq.Text = supList[i].SupplierID;
                lnkReq.Click += new EventHandler(lnkReq_Click);

                supCode.Controls.Add(lnkReq);
                row.Cells.Add(supCode);

                var supName = new TableCell();
                supName.Text = supList[i].Name;
                row.Cells.Add(supName);

                var supItemsTotal = new TableCell();
                var list = MainCont.GetItemSupplierListBySupId(supList[i].SupplierID);
                supItemsTotal.Text = list.Count.ToString();
                row.Cells.Add(supItemsTotal);

                var BtnDelete = new Button();
                BtnDelete.ID = "deleteID" + supList[i].SupplierID;
                BtnDelete.Text = "Delete";
                BtnDelete.Click += new EventHandler(BtnDelete_Click);
                var action = new TableCell();
                action.Controls.Add(BtnDelete);
                row.Cells.Add(action);

                TableSupList.Rows.Add(row);
            }
        }

        protected void BtnAddSup_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/storeSupplier.aspx");
        }

        protected void lnkReq_Click(object sender, EventArgs e)
        {
            var control = (LinkButton)sender;
            if (control != null)
            {
                Response.Redirect("~/Views/storeSupplier.aspx?SupID=" + control.Text);
            }
        }

        protected void BtnDelete_Click(object sender, EventArgs e)
        {
            var control = (Button)sender;
            if (control != null)
            {
                //String SupID:
                if (MainCont.delSupplier(control.ID.Substring(8)))
                    Response.Redirect("~/Views/storeMaintainSuppliers.aspx?SupID=" + "Deleted");
                else
                    Response.Write("<script language='javascript'>alert('Only Suppliers not assigned Tendered Items can be deleted.');</script>");
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
                        authorization = false;
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