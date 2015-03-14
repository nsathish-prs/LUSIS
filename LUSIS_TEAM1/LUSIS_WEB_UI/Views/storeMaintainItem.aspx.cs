using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeMaintainItem : System.Web.UI.Page
    {
        private readonly MainController MainCont = new MainController();
        private List<Item> itemList = null;
        private List<ItemSupplierDetail> itemSupList = null;
        private List<Supplier> supList = null;
        private List<String> catList = null;
        private String selected = "";
        private String ICode = "";
        private int select1 = -1;
        private int select2 = -1;
        private int select3 = -1;

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            if (IsPostBack == false)
                LblErrorText.Text = "";

            // Save ViewState
            ICode = LblItemCode.Text;
            select1 = DDLSupplier1.SelectedIndex;
            select2 = DDLSupplier2.SelectedIndex;
            select3 = DDLSupplier3.SelectedIndex;

            // Check for Add New Item !!
            if (Request.Params["N"] != null)
            {
                Session["Item"] = null;
            }

            // Load storeMaintainItem.aspx in Add New Item mode.
            if (Request.Params["N"] != null)
            {
                LblHeadTitle.Text = "Maintain Catalog Items [Add New]";
                MultiViewItemSelect.ActiveViewIndex = 0;
                TxtBinID.ReadOnly = true;
                TxtBinID.Text = "Auto-Generated";
                BtnDone.Text = "Save Item";
                supList = MainCont.getSupplierList();
                if (supList != null)
                {
                    var supStringList = new List<String>();
                    supStringList.Add("Not Selected");
                    supStringList.Add("None");
                    foreach (var s in supList)
                    {
                        supStringList.Add(s.SupplierID);
                    }

                    checkDDLselected(supStringList);
                }
            }
            // Load storeMaintainItem.aspx in Edit Item mode.
            else
            {
                TxtItemCode.ReadOnly = true;
                TxtBinID.ReadOnly = true;
                TxtBinID.Text = "Auto-Generated";
                catList = MainCont.getItemCategoryList();

                if (IsPostBack == false)
                {
                    if (catList != null)
                        catList.Insert(0, "Not Selected");
                    else
                    {
                        catList = new List<String>();
                        catList.Add("Not Selected");
                    }
                    
                    DDLCategory.DataSource = catList;
                    DDLCategory.DataBind();
                }

                if (Session["C"] != null)
                {
                    for (var x = 0; x < catList.Count; x++)
                    {
                        if (Session["C"].ToString() == catList[x])
                            DDLCategory.SelectedIndex = x;
                    }
                }

                if (DDLCategory.SelectedIndex == -1 || DDLCategory.SelectedIndex == 0)
                {
                    var descList = new List<String>();
                    descList.Add("Not Selected");
                    DDLDescription.DataSource = descList;
                    DDLDescription.DataBind();

                    clearFormControl();
                }
                else
                {
                    if (Session["D"] != null)
                    {
                        selected = Session["D"].ToString();
                    }
                    else
                        selected = DDLDescription.SelectedValue;

                    var descList = new List<String>();
                    descList.Add("Not Selected");
                    itemList = MainCont.getItemListByCategory(DDLCategory.SelectedValue);
                    
                    if (itemList != null)
                    {
                        foreach (var i in itemList)
                        {
                            descList.Add(i.Description);
                        }
                    }
                    DDLDescription.DataSource = descList;
                    DDLDescription.DataBind();
                    for (var x = 0; x < descList.Count; x++)
                    {
                        if (descList[x] == selected)
                        DDLDescription.SelectedIndex = x;
                    }

                    // Load current data into Form controls
                    if (DDLDescription.SelectedIndex != -1 && DDLDescription.SelectedIndex != 0)
                    {
                        var x = DDLDescription.SelectedIndex - 1;
                        LblItemCode.Text = itemList[x].ItemCode;
                        LblCategory.Text = itemList[x].Category;
                        LblDescription.Text = itemList[x].Description;
                        LblReorder.Text = itemList[x].ReOrderLevel.ToString();
                        LblRQuantity.Text = itemList[x].ReOrderQuantity.ToString();
                        LblUOM.Text = itemList[x].UnitOfMeasure;
                        LblBinID.Text = itemList[x].BinId.ToString();
                        LblStatus.Text = itemList[x].Status;

                        // Get Supplier & Item-Supplier Data.
                        supList = MainCont.getSupplierList();
                        itemSupList = MainCont.getItemSupplierList(itemList[x].ItemCode);
                    }
                    else
                    {
                        clearFormControl();
                    }

                    // Load Supplier & Item-Supplier Data in Table.
                    if (supList != null)
                    {
                        var supStringList = new List<String>();
                        supStringList.Add("Not Selected");
                        supStringList.Add("None");
                        foreach (var s in supList)
                        {
                            supStringList.Add(s.SupplierID);
                        }

                        var icount = itemSupList.Count;

                        checkDDLselected(supStringList);
                        //DDLSupplier1.DataSource = supStringList;
                        //DDLSupplier1.DataBind();
                        //DDLSupplier2.DataSource = supStringList;
                        //DDLSupplier2.DataBind();
                        //DDLSupplier3.DataSource = supStringList;
                        //DDLSupplier3.DataBind();

                        // Fill Current Data To Item-Supplier Table
                        var x = 0;
                        if (x < icount)
                        {
                            if (DDLSupplier1.SelectedIndex == -1 || DDLSupplier1.SelectedIndex == 0)
                            {
                                for (var z = 0; z < supList.Count; z++)
                                {
                                    if (supList[z].SupplierID == itemSupList[0].SupplierID)
                                        DDLSupplier1.SelectedIndex = z + 2;
                                }
                                LblSupplier1.Text = itemSupList[0].Supplier.Name;
                                TxtPrice1.Text = itemSupList[0].UnitPrice.ToString();
                            }

                            if (DDLSupplier1.SelectedIndex == 1)
                            {
                                LblSupplier1.Text = "None";
                                TxtPrice1.Text = "";
                            }
                            x++;
                        }
                        if (x < icount)
                        {
                            if (DDLSupplier2.SelectedIndex == -1 || DDLSupplier2.SelectedIndex == 0)
                            {
                                for (var z = 0; z < supList.Count; z++)
                                {
                                    if (supList[z].SupplierID == itemSupList[1].SupplierID)
                                        DDLSupplier2.SelectedIndex = z + 2;
                                }
                                LblSupplier2.Text = itemSupList[1].Supplier.Name;
                                TxtPrice2.Text = itemSupList[1].UnitPrice.ToString();
                                
                            }
                            if (DDLSupplier2.SelectedIndex == 1)
                            {
                                LblSupplier2.Text = "None";
                                TxtPrice2.Text = "";
                            }
                            x++;
                        }
                        if (x < icount)
                        {
                            if (DDLSupplier3.SelectedIndex == -1 || DDLSupplier3.SelectedIndex == 0)
                            {
                                for (var z = 0; z < supList.Count; z++)
                                {
                                    if (supList[z].SupplierID == itemSupList[2].SupplierID)
                                        DDLSupplier3.SelectedIndex = z + 2;
                                }
                                LblSupplier3.Text = itemSupList[2].Supplier.Name;
                                TxtPrice3.Text = itemSupList[2].UnitPrice.ToString();
                            }
                            if (DDLSupplier3.SelectedIndex == 1)
                            {
                                LblSupplier3.Text = "None";
                                TxtPrice3.Text = "";
                            }
                            x++;
                        }
                    }
                }
            }
            Session["C"] = null;
            Session["D"] = null;
            Session["ICode"] = null;
        }

        protected void BtnDone_Click(object sender, EventArgs e)
        {
            var outInt = 0;
            double outDouble = 0;
            var item = new Item();
            item.ItemCode = (LblItemCode.Text == "" ? TxtItemCode.Text : LblItemCode.Text);
            if (item.ItemCode == "")
                LblErrorText.Text += "Error :: Item Code cannot be Empty!!<br />";
            item.Category = (TxtCategory.Text == "" ? LblCategory.Text : TxtCategory.Text);
            if (item.Category == "")
                LblErrorText.Text += "Error :: Item Category cannot be Empty!!<br />";
            item.Description = (TxtDescription.Text == "" ? LblDescription.Text : TxtDescription.Text);
            if (item.Description == "")
                LblErrorText.Text += "Error :: Item Description cannot be Empty!!<br />";

            var reorderLvl = (TxtReorder.Text == "" ? LblReorder.Text : TxtReorder.Text);
            reorderLvl = (reorderLvl == "" ? "0" : reorderLvl);
            if (int.TryParse(reorderLvl, out outInt))
                item.ReOrderLevel = Convert.ToInt32(reorderLvl);
            else
                LblErrorText.Text += "Error :: Reorder Level should be Integer !!<br />";

            var reorderQty = (TxtRQuantity.Text == "" ? LblRQuantity.Text : TxtRQuantity.Text);
            reorderQty = (reorderQty == "" ? "0" : reorderQty);
            if (int.TryParse(reorderQty, out outInt))
                item.ReOrderQuantity = Convert.ToInt32(reorderQty);
            else
                LblErrorText.Text += "Error :: Reorder Quantity should be Integer !!<br />";

            item.UnitOfMeasure = (TxtUOM.Text == "" ? LblUOM.Text : TxtUOM.Text);
            if (item.UnitOfMeasure == "")
                LblErrorText.Text += "Error :: Unit Of Measure cannot be Empty!!<br />";

            if (TxtBinID.Text == "Auto-Generated")
                item.BinId = 1;
            else
                item.BinId = Convert.ToInt32(TxtBinID.Text == "" ? LblBinID.Text : TxtBinID.Text);
            item.Status = (RBLStatus.SelectedValue == "" ? LblStatus.Text : RBLStatus.SelectedValue);
            item.Status = (LblStatus.Text == "" | LblStatus.Text == "Active" | LblStatus.Text == "Inactive" ? item.Status : LblStatus.Text);
            item.Status = (item.Status == "" ? "Active" : item.Status);

            var iSupList = new List<ItemSupplierDetail>();

            var iSup1 = new ItemSupplierDetail();
            iSup1.ItemCode = item.ItemCode;
            iSup1.SupplierID = DDLSupplier1.SelectedValue;
            var price1 = (TxtPrice1.Text == "" ? "0" : TxtPrice1.Text);
            if (double.TryParse(price1, out outDouble))
                iSup1.UnitPrice = Convert.ToDouble(price1);
            else
                LblErrorText.Text += "Error :: Unit Price should be Decimal !!<br />";

            var iSup2 = new ItemSupplierDetail();
            iSup2.ItemCode = item.ItemCode;
            iSup2.SupplierID = DDLSupplier2.SelectedValue;
            var price2 = (TxtPrice2.Text == "" ? "0" : TxtPrice2.Text);
            if (double.TryParse(price2, out outDouble))
                iSup2.UnitPrice = Convert.ToDouble(price2);
            else
                LblErrorText.Text += "Error :: Unit Price should be Decimal !!<br />";
           
            var iSup3 = new ItemSupplierDetail();
            iSup3.ItemCode = item.ItemCode;
            iSup3.SupplierID = DDLSupplier3.SelectedValue;
            var price3 = (TxtPrice3.Text == "" ? "0" : TxtPrice3.Text);
            if (double.TryParse(price3, out outDouble))
                iSup3.UnitPrice = Convert.ToDouble(price3);
            else
                LblErrorText.Text += "Error :: Unit Price should be Decimal !!<br />";
            
            if (LblSupplier1.Text != "" && LblSupplier1.Text != "None")
                iSupList.Add(iSup1);
            if (LblSupplier2.Text != "" && LblSupplier2.Text != "None")
                iSupList.Add(iSup2);
            if (LblSupplier3.Text != "" && LblSupplier3.Text != "None")
                iSupList.Add(iSup3);

            // Response.AppendCookie(new HttpCookie("Test", iSupList.Count.ToString()));

            var control = (Button)sender;
            if (LblErrorText.Text == "")
            {
                if (control.Text == "Save Item")
                {
                    if (item.ItemCode != "")
                    {
                        if (MainCont.addItem(item))
                        {
                            //MainCont.setItemSupplierList();
                            if (MainCont.setItemSupplierList(iSupList, item.ItemCode))
                            {
                                Response.Write("<script language='javascript'>alert('Success :: Added New Item Details.');</script>");
                                Response.Write("<script language='javascript'>window.location.href = 'storeMaintainItem.aspx?N=0';</script>");
                            }
                            else
                                Response.Write("<script language='javascript'>alert('Error :: Fail to Add ItemSupplier.');</script>");
                        }
                        else
                            Response.Write("<script language='javascript'>alert('Error :: Fail to Add Item.');</script>");
                    }
                    else
                        Response.Write("<script language='javascript'>alert('Error :: ItemCode cannot be blank.');</script>");

                    //MainCont.setItemSupplierList();
                }
                else if (control.Text == "Update")
                {
                    if (item.ItemCode != "")
                    {
                        if (MainCont.setItem(item))
                        {
                            Session["C"] = LblCategory.Text;
                            Session["D"] = LblDescription.Text;

                            //MainCont.setItemSupplierList();
                            if (MainCont.setItemSupplierList(iSupList, item.ItemCode))
                            {
                                Response.Write("<script language='javascript'>alert('Success :: Updated Item Details.');</script>");
                                Response.Write("<script language='javascript'>window.location.href = 'storeMaintainItem.aspx';</script>");
                            }
                            else
                                Response.Write("<script language='javascript'>alert('Error :: Fail to Update ItemSupplier.');</script>");
                        }
                        else
                            Response.Write("<script language='javascript'>alert('Error :: Fail to Update Item.');</script>");
                    }
                    else
                        Response.Write("<script language='javascript'>alert('Error :: No Item Selected.');</script>");
                }
            }
            else
                LblErrorText.Text += "Error :: Failed to update/save record !!<br />";
        }

        protected void BtnCancel_Click(object sender, EventArgs e)
        {
            if (Request.Params["N"] != null)
            {
                Response.Redirect("~/Views/storeMaintainItem.aspx?N=0");
            }
            else
                Response.Redirect("~/Views/storeMaintainItem.aspx");
        }

        private void clearFormControl()
        {
            LblItemCode.Text = "";
            LblCategory.Text = "";
            LblDescription.Text = "";
            LblReorder.Text = "";
            LblRQuantity.Text = "";
            LblUOM.Text = "";
            LblBinID.Text = "";
            LblStatus.Text = "";

            TxtItemCode.Text = "";
            TxtCategory.Text = "";
            TxtDescription.Text = "";
            TxtReorder.Text = "";
            TxtRQuantity.Text = "";
            TxtUOM.Text = "";

            TxtBinID.ReadOnly = true;
            TxtBinID.Text = "Auto-Generated";

            RBLStatus.SelectedIndex = -1;

            supList = null;
            itemSupList = null;

            var eList = new List<String>();
            DDLSupplier1.DataSource = eList;
            DDLSupplier1.DataBind();
            DDLSupplier2.DataSource = eList;
            DDLSupplier2.DataBind();
            DDLSupplier3.DataSource = eList;
            DDLSupplier3.DataBind();

            LblSupplier1.Text = "None";
            LblSupplier2.Text = "None";
            LblSupplier3.Text = "None";

            TxtPrice1.Text = "";
            TxtPrice2.Text = "";
            TxtPrice3.Text = "";
        }

        private void checkDDLselected(List<String> supStringList)
        {
            if (LblItemCode.Text == ICode)
            {
                //int select1 = DDLSupplier1.SelectedIndex;
                //int select2 = DDLSupplier2.SelectedIndex;
                //int select3 = DDLSupplier3.SelectedIndex;

                DDLSupplier1.DataSource = supStringList;
                DDLSupplier1.DataBind();
                DDLSupplier2.DataSource = supStringList;
                DDLSupplier2.DataBind();
                DDLSupplier3.DataSource = supStringList;
                DDLSupplier3.DataBind();

                if (select1 == -1 || select1 == 0)
                    LblSupplier1.Text = "None";
                else if (select1 == 1)
                {
                    DDLSupplier1.SelectedIndex = select1;
                    LblSupplier1.Text = "None";
                    TxtPrice1.Text = "";
                }
                else
                {
                    DDLSupplier1.SelectedIndex = select1;
                    LblSupplier1.Text = supList[select1 - 2].Name;
                }

                if (select2 == -1 || select2 == 0)
                    LblSupplier2.Text = "None";
                else if (select2 == 1)
                {
                    DDLSupplier2.SelectedIndex = select2;
                    LblSupplier2.Text = "None";
                    TxtPrice2.Text = "";
                }
                else
                {
                    DDLSupplier2.SelectedIndex = select2;
                    LblSupplier2.Text = supList[select2 - 2].Name;
                }

                if (select3 == -1 || select3 == 0)
                    LblSupplier3.Text = "None";
                else if (select3 == 1)
                {
                    DDLSupplier3.SelectedIndex = select3;
                    LblSupplier3.Text = "None";
                    TxtPrice3.Text = "";
                }
                else
                {
                    DDLSupplier3.SelectedIndex = select3;
                    LblSupplier3.Text = supList[select3 - 2].Name;
                }
            }
            else
            {
                DDLSupplier1.DataSource = supStringList;
                DDLSupplier1.DataBind();
                DDLSupplier2.DataSource = supStringList;
                DDLSupplier2.DataBind();
                DDLSupplier3.DataSource = supStringList;
                DDLSupplier3.DataBind();
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