<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeVoucherList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeVoucherList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-8">
        
        <h1 class="page-header"> Discrepancy Voucher List</h1>

        <div class="col-lg-12">

            <div class="table-responsive">

                <asp:Table ID="TableVouList" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Voucher ID</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Date</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Raised By</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Approval By</asp:TableHeaderCell>
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
