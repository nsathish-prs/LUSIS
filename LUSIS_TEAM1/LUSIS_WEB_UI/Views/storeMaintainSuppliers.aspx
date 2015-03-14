<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeMaintainSuppliers.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeMaintainSuppliers" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header">Maintain Suppliers</h1>

        <div style="margin-bottom:1.5%">

            <div class="col-lg-4"><asp:Button ID="BtnAddSup" runat="server" 
                    Text="Add New Supplier" class="btn btn-primary" type="submit" 
                    onclick="BtnAddSup_Click" /></div>
        
        <div style="clear:both"></div>
        
        </div>

        <div class="col-lg-8">

        <div class="table-responsive" style="margin-top:1.5%">

                <asp:Table ID="TableSupList" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Supplier Code </asp:TableHeaderCell>
                        <asp:TableHeaderCell>Supplier Name</asp:TableHeaderCell>
                        <asp:TableHeaderCell>No. Of Items Supplied</asp:TableHeaderCell>
                        <asp:TableHeaderCell ColumnSpan="2">Action</asp:TableHeaderCell>
                    </asp:TableHeaderRow>
               
                    <asp:TableRow>
                                        
                    </asp:TableRow>

                </asp:Table>

            </div>

            </div>

    </div>
    
</div>

</asp:Content>
