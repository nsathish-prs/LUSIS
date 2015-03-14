<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeMaintainItem.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeMaintainItem" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-8">
        
        <h1 class="page-header">
            <asp:Label ID="LblHeadTitle" runat="server" 
                Text="Maintain Catalog Items [Update]"></asp:Label>
        </h1>
        

        <div style="margin-bottom:1.5%; color:white">

        <div class="col-lg-10"><h4><asp:HyperLink ID="LinkUpdate" runat="server" ForeColor="Black" Font-Underline="true" 
                NavigateUrl="~/Views/storeMaintainItem.aspx">Update An Existing Item</asp:HyperLink>&nbsp;&nbsp;|&nbsp;&nbsp;<asp:HyperLink 
                ID="LinkAdd" ForeColor="Black" Font-Underline="true" runat="server" NavigateUrl="~/Views/storeMaintainItem.aspx?N=0">Add New Item To Catalog</asp:HyperLink>
            </h4></div>

        <div class="col-lg-8"><asp:Label ID="LblErrorText" runat="server" Text=""></asp:Label></div>
        

        <asp:MultiView ID="MultiViewItemSelect" runat="server" ActiveViewIndex="1">
            <asp:View ID="ViewAddNewItem" runat="server">
            <div style="clear:both"></div>
            </asp:View>
            <asp:View ID="ViewEditItem" runat="server">     
            <div style="clear:both"><br /></div>
        
            <div class="col-lg-12">
            
            <div class="col-lg-4" style="margin-bottom:1.5%">
                <asp:Label ID="LblSelectCat" runat="server" Text="Select Item Category: "></asp:Label>
            </div>

                <div class="col-lg-4" style="margin-bottom:1.5%"> 
                    <asp:DropDownList ID="DDLCategory" runat="server" CssClass="form-control" 
                        AutoPostBack="True"> </asp:DropDownList> </div>

            <div style="clear:both;"></div>

            <div class="col-lg-4">
                <asp:Label ID="LblSelectItem" runat="server" Text="Select Item By Description:"></asp:Label>
            </div>

                <div class="col-lg-4"> 
                    <asp:DropDownList ID="DDLDescription" runat="server" 
                        CssClass="form-control" AutoPostBack="True"> </asp:DropDownList> </div>

            <div style="clear:both"></div>
           
            </div>

            </asp:View>
        </asp:MultiView>
        

        
        </div>



            <div class="col-lg-12" style="color:White">
                <hr /> 
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Item Code</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblItemCode" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox CssClass="form-control" 
                    ID="TxtItemCode" runat="server" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Item Category</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblCategory" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox CssClass="form-control" 
                    ID="TxtCategory" runat="server" ></asp:TextBox></div>
           <%-- 
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Email</div>
            <div class="col-lg-4" style="margin-top:1.5%"><asp:TextBox ID="supEmail" CssClass="form-control" runat="server" ></asp:TextBox></div>
            --%>
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Item Description</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblDescription" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox ID="TxtDescription" 
                    runat="server" CssClass="form-control" Rows="4"></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Reorder Level</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblReorder" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox ID="TxtReorder" 
                    runat="server" CssClass="form-control" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Reorder Quantity</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblRQuantity" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox ID="TxtRQuantity" 
                    runat="server" CssClass="form-control" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Unit of Measure</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblUOM" 
                    runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox ID="TxtUOM" 
                    CssClass="form-control" runat="server" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Bin ID</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblBinID" runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:TextBox ID="TxtBinID" 
                    runat="server" CssClass="form-control" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Status</div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:Label ID="LblStatus" runat="server"></asp:Label>
                </div>
            <div class="col-lg-4" style="margin-top:1.5%">
                <asp:RadioButtonList ID="RBLStatus" runat="server" RepeatDirection="Horizontal">
                    <asp:ListItem>Active</asp:ListItem>
                    <asp:ListItem>Inactive</asp:ListItem>
                </asp:RadioButtonList>
                    </div>

            <div style="clear:both"></div>
        
        </div>

        <div style="clear:both"></div>

        <div class="col-lg-10">

        <div class="table-responsive" style="margin-top:1.5%">

                <asp:Table ID="TableItemSup" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Supplier Code </asp:TableHeaderCell>
                        <asp:TableHeaderCell Width="50%">Supplier Name</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Unit Price (S$)</asp:TableHeaderCell>
                        <%--<asp:TableHeaderCell>Action</asp:TableHeaderCell>--%>
                    </asp:TableHeaderRow>
               
                    <asp:TableRow runat="server">
                        <asp:TableCell runat="server">
                            <asp:DropDownList ID="DDLSupplier1" CssClass="form-control" runat="server" AutoPostBack="True">
                            </asp:DropDownList>
                        </asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:Label ID="LblSupplier1" runat="server" Text="None"></asp:Label></asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:TextBox ID="TxtPrice1" CssClass="form-control" runat="server"></asp:TextBox></asp:TableCell> 
                    </asp:TableRow>

                    <asp:TableRow runat="server">
                        <asp:TableCell runat="server">
                            <asp:DropDownList ID="DDLSupplier2" CssClass="form-control" runat="server" AutoPostBack="True">
                            </asp:DropDownList>
                        </asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:Label ID="LblSupplier2" runat="server" Text="None"></asp:Label></asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:TextBox ID="TxtPrice2" CssClass="form-control" runat="server"></asp:TextBox></asp:TableCell>
                    </asp:TableRow>
                    <asp:TableRow runat="server">
                        <asp:TableCell runat="server">
                            <asp:DropDownList ID="DDLSupplier3" CssClass="form-control" runat="server" AutoPostBack="True">
                            </asp:DropDownList>
                        </asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:Label ID="LblSupplier3" runat="server" Text="None"></asp:Label></asp:TableCell>
                        <asp:TableCell runat="server">
                            <asp:TextBox ID="TxtPrice3" CssClass="form-control" runat="server"></asp:TextBox></asp:TableCell>
                    </asp:TableRow>

                </asp:Table>

            </div>
            <hr />
            <div style="clear:both"></div>

            <div>
                            <asp:Button ID="BtnDone" runat="server" Text="Update" class="btn btn-primary" 
                                type="submit" onclick="BtnDone_Click" />
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <asp:Button ID="BtnCancel" runat="server" Text="Cancel" class="btn btn-primary" 
                                type="submit" onclick="BtnCancel_Click" />
            </div>
        </div>
    </div>
</div>

</asp:Content>

