<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storePOplaced.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storePOplaced" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header"> Purchase Orders Placed  </h1>

        <div class="col-lg-4"><h4>Orders Placed to various Suppliers</h4></div>

            <div class="col-lg-4"><asp:Button ID="pendingList" runat="server" Text="Show Pending" class="btn btn-sm btn-primary" type="submit" /></div>
        
        <div style="clear:both"></div>
        
        <div class="col-lg-8">

            <div class="table-responsive">

                <asp:Table ID="TableRaiseReq" CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Supplier ID</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Supplier Name</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Number of Items</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Status</asp:TableHeaderCell>
                    </asp:TableHeaderRow>
               
                    <asp:TableRow>
                                        
                    </asp:TableRow>

                </asp:Table>
        
            </div>

        </div>


    </div>

</div>

</asp:Content>
