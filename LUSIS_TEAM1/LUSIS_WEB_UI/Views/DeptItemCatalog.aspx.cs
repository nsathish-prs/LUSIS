using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptItemCatalog : System.Web.UI.Page
    {
        readonly MainController Controller = new MainController();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            var itemCatalog = Controller.getItemCatalog();
            var count = 0;
            foreach( var item in itemCatalog)
            {
                var row = new TableRow();
                var category = new TableCell();
                category.Text = item.Category;
                row.Cells.Add(category);

                var description = new TableCell();
                description.Text = item.Description;
                row.Cells.Add(description);

                var unit = new TableCell();
                unit.Text = item.UnitOfMeasure;
                row.Cells.Add(unit);

                var cb = new CheckBox();
                cb.ID = "idSelect" + count;
                cb.Checked = false;
                var select = new TableCell();
                select.Controls.Add(cb);
                row.Cells.Add(select);
                count++;

                TableCatalog.Rows.Add(row);
            }

        }
        //newly added to the local solution.
        protected void btnAddToList_Click(object sender, EventArgs e)
        {
            var listSelectedItems = new List<Item>();
            //List<Item> listSelectedItems = new List<RequisitionDetail>();
            var count = -1;

            if (Session["selectedItems"] != null)
            {
                listSelectedItems =(List<Item>) Session["selectedItems"];
                //listSelectedItems = (List<RequisitionDetail>)Session["selectedItems"];
            }
      
            foreach (TableRow row in TableCatalog.Rows)
            {  
                var itemExists = false;

                if (row.FindControl("idSelect" + count) != null)
                {
                    var cbSelect = (CheckBox)row.FindControl("idSelect" + count);
                    if (cbSelect.Checked == true)
                    {
                        if (listSelectedItems != null)
                        {
                            foreach (var i in listSelectedItems)
                            {
                                if (i.Description == row.Cells[1].Text)
                                {
                                    itemExists = true;
                                    break;
                                }

                            }
                            if (itemExists == false)
                            {
                                var selectedItem = new Item();
                                //selectedItem.ItemCode = Controller.selectItemByDescription(row.Cells[1].Text).ItemCode;
                                selectedItem.Description = row.Cells[1].Text;
                                selectedItem.CurrentQuantity = 0;
                                listSelectedItems.Add(selectedItem);
                            }
                        }

                    }
                }
                count++;
            }
            if (Session["selectedItems"] == null)
            {
                Session["selectedItems"] = listSelectedItems;
            }
            else
            {
               Session["selectedItems"] = listSelectedItems;
            }
            if (Request.Params["ReqID"] != null)
            {
                var reqID = Request.Params["ReqID"];
                Response.Redirect("~/Views/DeptNewRequisition.aspx?ReqID=" + reqID);
            } else
                Response.Redirect("~/Views/DeptNewRequisition.aspx");
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