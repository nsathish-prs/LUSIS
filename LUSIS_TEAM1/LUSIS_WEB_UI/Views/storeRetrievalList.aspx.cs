using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;
using LUSIS_CONTROLLER.Controller;
using System.Drawing;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeRetrievalList : System.Web.UI.Page
    {
        EF_Facade facade = new EF_Facade();

        int subHeaderWidth1 = 250;
        int subHeaderWidth2 = 200;
        int subHeaderWidth3 = 200;

        readonly MainController mainController = new MainController();
        List<RetrievalByItem> retrievalItems;

        // Get Retrieval Id
        int retrivedByItemID;
        readonly Dictionary<String, DisbursementList> deptDisbListMap = new Dictionary<string, DisbursementList>();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            
            if (IsPostBack)
                MultiView1.SetActiveView(View2);
            else
                MultiView1.SetActiveView(View1);

            int itemQty = 0;
            // GetPendingRetrievalItems
            // For View1
            

            retrievalItems = mainController.receiveRetrievalByItem();
            if (retrievalItems.Count > 0)
            {
                LblNote.Visible = false;
                GridView1.DataSource = retrievalItems;
                GridView1.DataBind();


                // For View2
                createTableHeader();
                int count = 0;
                foreach (var item in retrievalItems)
                {
                    count++;

                    TableRow rowRetriID = new TableRow();
                    retrivedByItemID = item.RetrievalID;

                    var itemDescCell = new TableCell();
                    
                    var itemCodeField = new HiddenField();
                    itemCodeField.Value = item.Item.ItemCode;

                    var LblDescription = new Label();
                    LblDescription.Text = item.Item.Description;

                    // Two Cells In MainTable Cell 1
                    itemDescCell.Controls.Add(itemCodeField);
                    itemDescCell.Controls.Add(LblDescription);

                    TableCell currQtyCell = new TableCell();

                    TextBox LblCurrQty = new TextBox();
                    LblCurrQty.Style.Add("border", "none");
                    LblCurrQty.Style.Add("background-color", "Transparent");
                    LblCurrQty.Enabled = false;
                    LblCurrQty.ID = "AvailableQty_" + count;

                    if (item.Item.CurrentQuantity >= item.RequestedQty)
                    {
                        LblCurrQty.Text = (item.Item.CurrentQuantity).ToString();
                        // retrievedQtyCell.BackColor = System.Drawing.Color.White;
                    }
                    else
                    {
                        LblCurrQty.Text = (item.Item.CurrentQuantity).ToString();
                        currQtyCell.BackColor = System.Drawing.Color.Gray;
                        currQtyCell.ForeColor = System.Drawing.Color.White;
                    }
                    // Add Label to MainTable Cell 2
                    currQtyCell.Controls.Add(LblCurrQty);

                    // Available & retrieved quantity validation
                    CustomValidator validator = new CustomValidator();
                    validator.ID = "validator_" + LblCurrQty.ID;
                    validator.ErrorMessage = "Please check retrieved quantities";
                    validator.ForeColor = Color.Red;
                    validator.ControlToValidate = LblCurrQty.ID;
                    validator.ValidationGroup = "QuantityValidation";
                    validator.ValidateEmptyText = true;
                    validator.ServerValidate += new ServerValidateEventHandler(validateQuantities);
                    currQtyCell.Controls.Add(validator);

                    // Create Row (Cell 1 & Cell 2) Of Retrieval Record.
                    rowRetriID.Cells.Add(itemDescCell);
                    rowRetriID.Cells.Add(currQtyCell);

                    // Inner Table Cell
                    var DisbCell = new TableCell();
                    DisbCell.CssClass = "table table-bordered table-hover table-striped";

                    var innerTable = new Table();
                    // table.CssClass = "table table-bordered table-hover table-striped";
                    innerTable.Style.Add("padding", "0px");
                    innerTable.Style.Add("width", "100%");
                    innerTable.Style.Add("padding-left", "5px");
                    innerTable.CellPadding = 10;

                    DisbCell.Controls.Add(innerTable);
                    DisbCell.ColumnSpan = 3;
                    DisbCell.Style.Add("padding", "0px");
                    DisbCell.Style.Add("padding-left", "5px");

                    var list1 = (item.DisbursementItems).ToList();
                    // list1.Add(list1.ElementAt(0));

                    itemQty = (int) item.Item.CurrentQuantity;
                    var qtyList = new List<int>();
                    var index = 0;

                    if (itemQty < 1)
                    {
                        for (var x = 0; x < list1.Count; x++)
                            qtyList.Add(0);
                    }
                    else if (itemQty < item.RequestedQty)
                    {
                        for (var x = 0; x < list1.Count; x++)
                        {
                            var initQty = (int)Math.Floor((double)list1[x].RequestedQty / (double)item.RequestedQty * (int)item.Item.CurrentQuantity);
                            qtyList.Add(initQty);
                            itemQty -= initQty;
                        }

                        for (var x = itemQty; x > 0; )
                        {
                            for (var y = 0; y < qtyList.Count && x > 0; y++)
                            {
                                qtyList[y] += 1;
                                x -= 1;
                            }
                        }
                    }
                    else
                    {
                        for (var x = 0; x < list1.Count; x++)
                        {
                            qtyList.Add((int)list1[x].RequestedQty);
                        }
                    }

                    int innerIndex = 0;
                    foreach (DisbursementItem disbItem in list1)
                    {
                        innerIndex++;

                        TableRow tableRow = new TableRow();
                        tableRow.Style.Add("padding-top", "20px");
                        tableRow.Style.Add("padding-bottom", "1.5%");
                        tableRow.Style.Add("border-bottom", "1px solid #777");

                        var deptCell = new TableCell();
                        var LblDeptName = new Label();
                        LblDeptName.Text = (disbItem.Department.Name).ToString();

                        var collPtField = new HiddenField();
                        collPtField.Value = disbItem.Department.CollectionPoint.ToString();

                        var repIDField = new HiddenField();
                        // Write in Facade
                        repIDField.Value = disbItem.Department.Employees.Where(i => i.AdditionalRole == "Representative").First().EmpID;

                        var deptCodeField = new HiddenField();
                        deptCodeField.Value = disbItem.Department.DeptCode.ToString();

                        // Add Hidden Fields
                        deptCell.Controls.Add(repIDField);
                        deptCell.Controls.Add(collPtField);
                        deptCell.Controls.Add(deptCodeField);

                        // First Shown Cell
                        deptCell.Controls.Add(LblDeptName);
                        deptCell.Width = subHeaderWidth1;

                        var reqQtyCell = new TableCell();
                        var LblReqQty = new Label();
                        LblReqQty.Text = (disbItem.RequestedQty).ToString();
                        LblReqQty.Style.Add("padding-left", "5px");

                        // Second Shown Cell
                        reqQtyCell.Controls.Add(LblReqQty);
                        reqQtyCell.Width = subHeaderWidth2;

                        var actQtyCell = new TableCell();
                        var txtActQty = new TextBox();
                        txtActQty.ID = LblCurrQty.ID + "_" + innerIndex;
                        txtActQty.CssClass = "form-control";
                        txtActQty.Style.Add("margin-top","1.5%");
                        txtActQty.Style.Add("margin-bottom", "2.5%");
                        actQtyCell.Style.Add("padding-right", "2%");
                        // int currentQty = Convert.ToInt32(item.Item.CurrentQuantity);

                        // Assign Initial Actual Quantity
                        txtActQty.Text = qtyList[index].ToString();
                        index++;

                        // txtActQty.Text = (disbItem.RetrievedQuantity).ToString();
                        txtActQty.Style.Add("padding-left", "5px");

                        // Third Shown Cell
                        actQtyCell.Controls.Add(txtActQty);
                        actQtyCell.Width = subHeaderWidth3;

                        // Table Row For Inner Table
                        tableRow.Cells.Add(deptCell);
                        tableRow.Cells.Add(reqQtyCell);
                        tableRow.Cells.Add(actQtyCell);

                        innerTable.Rows.Add(tableRow);
                    }

                    // Create Row (Cell 3) Of Retrieval Record.
                    rowRetriID.Cells.Add(DisbCell);

                    // Add Entire Retrival Record Row to Main Table.
                    TableDisbList.Rows.Add(rowRetriID);
                }
            }

            else if (retrievalItems.Count <= 0)
            {
                GridView1.Visible = false;
                genDisbursementBtn.Visible = false;
                LblNote.Visible = true;
                LblNote.Text = "There is currently no pending retrieval items !!";
                LblNote.ForeColor = Color.Red;
                Response.Write("<script language='javascript'>alert('There is currently no pending retrieval items !!');</script>");
            }
        }


        protected void validateQuantities(object source, ServerValidateEventArgs args)
        {
            try
            {
                int availableQty = Convert.ToInt32(args.Value);

                CustomValidator validator = (CustomValidator)source;
                String controlId = validator.ControlToValidate;
                String count = controlId.Split(new char[] { '_' })[1];

                RetrievalByItem item = retrievalItems.ElementAt(Convert.ToInt32(count) - 1);


                int index = 0;
                int totalQty = 0;

                List<DisbursementItem> list1 = (item.DisbursementItems).ToList();
                //list1.Add(list1.ElementAt(0));

                foreach (DisbursementItem disbItem in list1)
                {
                    index++;
                    TextBox retQty = (TextBox)TableDisbList.FindControl(controlId + "_" + index);
                    totalQty += Convert.ToInt32(retQty.Text);
                }

                args.IsValid = (availableQty >= totalQty || totalQty == 0);
            }
            catch (Exception e)
            {
                args.IsValid = false;
            }
        }

        private void createTableHeader()
        {
            //<asp:TableHeaderRow  >
            //                                <asp:TableCell RowSpan="2" VerticalAlign="Middle">Item Description</asp:TableCell>
            //                                <asp:TableCell RowSpan="2" VerticalAlign="Middle">Retrieved Qty.</asp:TableCell>
            //                                <asp:TableCell ColumnSpan="3">Disbursement</asp:TableCell>
            //                            </asp:TableHeaderRow>

            var hRow = new TableHeaderRow();

            var hCell = new TableHeaderCell();
            hCell.RowSpan = 2;
            hCell.VerticalAlign = VerticalAlign.Middle;
            hCell.Text = "Item Description";
            hRow.Cells.Add(hCell);

            hCell = new TableHeaderCell();
            hCell.RowSpan = 2;
            hCell.VerticalAlign = VerticalAlign.Middle;
            hCell.Text = "Available Quantity";
            hRow.Cells.Add(hCell);

            hCell = new TableHeaderCell();
            hCell.ColumnSpan = 3;
            hCell.Text = "Disbursement";
            hRow.Cells.Add(hCell);

            TableDisbList.Rows.Add(hRow);

            //<asp:TableRow>
            //                                <asp:TableCell>Department Name</asp:TableCell>
            //                                <asp:TableCell>Requested</asp:TableCell>
            //                                <asp:TableCell>Actual</asp:TableCell>
            //                            </asp:TableRow>


            var subHeaderRow = new TableRow();
            
            var subHCell = new TableCell();
            subHCell.Text = "Department Name";
            subHCell.Style.Add("width", "33%");
            subHeaderRow.Cells.Add(subHCell);

            subHCell = new TableCell();
            subHCell.Text = "Requested";
            subHCell.Style.Add("width", "30%");
            subHeaderRow.Cells.Add(subHCell);

            subHCell = new TableCell();
            subHCell.Text = "Actual";
            subHCell.Style.Add("width", "37%");
            subHeaderRow.Cells.Add(subHCell);

            TableDisbList.Rows.Add(subHeaderRow);
        }

        protected void readTableData(String status)
        {
            var disbursementItems = new List<DisbursementItem>();

            var i = -1;
            foreach (TableRow tableRow in TableDisbList.Rows)
            {
                i++;
                if (i < 2)
                    continue;
                // RetrievalByItem retItem = new RetrievalByItem();

                var itemCode = ((HiddenField)tableRow.Cells[0].Controls[0]).Value;

                int availQty = Convert.ToInt32(((TextBox)tableRow.Cells[1].Controls[0]).Text);

                // Third cell has nested table
                var innerItemsTable = (Table)tableRow.Cells[2].Controls[0];

                int count = 0;
                foreach (TableRow itemsTableRow in innerItemsTable.Rows)
                {

                    String repIDField = ((HiddenField)itemsTableRow.Cells[0].Controls[0]).Value;
                    String collPtField = ((HiddenField)itemsTableRow.Cells[0].Controls[1]).Value;
                    String deptCodeField = ((HiddenField)itemsTableRow.Cells[0].Controls[2]).Value;
                    String retQty = ((TextBox)itemsTableRow.Cells[2].Controls[0]).Text;
                    if (retQty.Trim() == "")
                        retQty = "0";
                    count += Convert.ToInt32(retQty);

                    var disbList = new DisbursementList()
                    {
                        RepresentativeID = repIDField,
                        DisbursementDate = DateTime.Now,
                        Status = "Ready",
                        CollectionPoint = Convert.ToInt32(collPtField)
                    };

                    if (!deptDisbListMap.ContainsKey(deptCodeField))
                        deptDisbListMap.Add(deptCodeField, disbList);

                    var disbItem = new DisbursementItem()
                    {
                        RetrievalID = retrivedByItemID,
                        ItemCode = itemCode,
                        RetrievedQuantity = Convert.ToInt32(retQty.Trim()),
                        DeptCode = deptCodeField
                    };

                    disbursementItems.Add(disbItem);
                }

                if (availQty < count && count != 0)
                {
                    Response.Write("<script language='javascript'>alert('The actual value should be less than available value');</script>");
                    MultiView1.SetActiveView(View2);
                    return;
                }
            }

            if (retrievalItems.Count > 0)
            {
                mainController.generateDisb(disbursementItems, deptDisbListMap, status);
            }
            //else
            //{
            //    Response.Write("<script language='javascript'>alert('Currently no pending retrieval items are there.');</script>");
            //}
        }


        

        protected void genDisbursement_Click(object sender, EventArgs e)
        {
            MultiView1.SetActiveView(View2);
        }

        protected void btnSave_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid)
                return;

            MultiView1.SetActiveView(View1);
            readTableData("finalize");
           
            Page_Load(sender, e);
            MultiView1.SetActiveView(View1);
        }

        protected void BtnUpdate_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid)
                return;

            MultiView1.SetActiveView(View1);
            readTableData("");
            Page_Load(sender, e);
            MultiView1.SetActiveView(View1);
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