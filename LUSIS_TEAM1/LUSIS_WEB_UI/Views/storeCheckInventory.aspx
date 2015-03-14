<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeCheckInventory.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeCheckInventory1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-7">
        
        <h1 class="page-header"> New Discrepancy Voucher</h1>
        
        <div class="col-lg-12" style="margin-bottom:1.5%; color:White">Enter the Item details and Add to 
            the Discrepancy Voucher.</div>
        <div class="col-lg-12" style="margin-bottom:1.5%">
            <asp:Label ID="LblErrorText" runat="server" Text=""></asp:Label></div>

        <div style="clear:both"></div> 
        
        <div class="col-lg-12" style="color:White">
            <div style="clear:both"><br /></div>
            
            <div class="col-lg-4" style="margin-bottom:1.5%">
                <asp:Label ID="LblSelectCat" runat="server" Text="Select Item Category: "></asp:Label>
            </div>

            <div class="col-lg-6" style="margin-bottom:1.5%"> 
                <asp:DropDownList ID="DDLCategory" runat="server" CssClass="form-control" 
                    AutoPostBack="True"> </asp:DropDownList> </div>

            <div style="clear:both;"></div>

            <div class="col-lg-4" style="margin-bottom:1.5%">
                <asp:Label ID="LblSelectItem" runat="server" Text="Select Item By Description:"></asp:Label>
            </div>

            <div class="col-lg-6" style="margin-bottom:1.5%"> 
                <asp:DropDownList ID="DDLDescription" runat="server" 
                    CssClass="form-control" AutoPostBack="True"> </asp:DropDownList> </div>

            <div style="clear:both"></div>
           
            <div class="col-lg-4" style="margin-bottom:1.5%">
                <asp:Label ID="LblSelItemDesc" runat="server" Text="Selected Item Description:"></asp:Label></div>

            <div class="col-lg-6" style="margin-bottom:1.5%"><asp:TextBox ID="itemDesc" CssClass="form-control" runat="server"></asp:TextBox></div>

            <div class="col-lg-2" style="margin-bottom:1.5%">
                <asp:Button ID="btnAdd" runat="server" Text="Add" 
                    class="btn btn-sm btn-primary" type="submit" onclick="btnAdd_Click" />
            </div>
            
            <div style="clear:both"></div>

        </div>

        <div style="clear:both"></div>

        <div class="table-responsive col-lg-8" style="margin-top:1.5%">

           <%--<asp:Table ID="TableDiscripancyItems" CssClass="table table-bordered table-hover table-striped" runat="server">
               
                <asp:TableHeaderRow>
                    <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                    <asp:TableHeaderCell>Quantity Adjusted</asp:TableHeaderCell>
                    <asp:TableHeaderCell>Reason</asp:TableHeaderCell>
                </asp:TableHeaderRow>

            </asp:Table>--%>
                    <asp:GridView ID="GVTableDiscripancyItems" 
                CssClass="table table-bordered table-hover table-striped" runat="server" 
                AutoGenerateColumns="False"> 
                        <Columns>
                            <asp:BoundField DataField="Item.Description" HeaderText="Item Description" />
                            <asp:TemplateField HeaderText="Quantity" HeaderStyle-CssClass="header1" >
                                <ItemTemplate>
                                    <asp:TextBox CssClass="form-control" ID="TxtQty" runat="server" AutoPostBack="True" 
                                        OnTextChanged="txtQty_TextChanged" Text='<%# DataBinder.Eval(Container.DataItem, "Quantity") %>' ></asp:TextBox>
                                </ItemTemplate>

<HeaderStyle CssClass="header1"></HeaderStyle>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Reason" HeaderStyle-CssClass="header1"  >
                                <ItemTemplate>
                                    <asp:TextBox CssClass="form-control" ID="TxtReason" runat="server" AutoPostBack="True" 
                                        OnTextChanged="txtReason_TextChanged"  Text='<%# DataBinder.Eval(Container.DataItem, "Reason") %>' ></asp:TextBox>
                                </ItemTemplate>

<HeaderStyle CssClass="header1"></HeaderStyle>
                            </asp:TemplateField>
                        </Columns>
        </asp:GridView>
        
        </div>

        <div style="clear:both"></div>

        <div class="col-lg-4" style="margin-top:1.5%">
            <asp:Button ID="submitBtn" runat="server" Text="Submit" class="btn btn-primary" 
                type="submit" onclick="submitBtn_Click" />
        </div>

    </div>
    
</div>


</asp:Content>
