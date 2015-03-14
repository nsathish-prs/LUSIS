<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeVoucher.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeVoucher" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-8">
        
        <h1 class="page-header"> 
            <asp:Label ID="LblHeadTitle" runat="server" Text="Discrepancy Voucher []"></asp:Label>
        </h1>
        <div style="clear:both"></div>
        
        <div class="col-lg-12" style="line-height:2em; color:White">   
            <div>Voucher No: <asp:Label ID="LblVoucherNo" runat="server" Text="Label"></asp:Label></div>
            <div>Date: <asp:Label ID="LblDate" runat="server" Text="Label"></asp:Label></div>
            <div>Raised By: <asp:Label ID="LblRaisedBy" runat="server" Text="Label"></asp:Label></div>
            <div>Approval By: <asp:Label ID="LblApprovalBy" runat="server" Text="Label"></asp:Label></div>
            <br />
        </div>

        <div class="col-lg-12">

            <div class="table-responsive">

                <asp:Table ID="TableVouItems" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Item No</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Qty. Adjusted</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Reason</asp:TableHeaderCell>
                        <asp:TableHeaderCell ID="HeadStatus">Status</asp:TableHeaderCell>
                    </asp:TableHeaderRow>
               
                    <asp:TableRow>        
                    </asp:TableRow>
                </asp:Table>
        
            </div>

            <div class="col-lg-6">
                    
                    <asp:Image ID="ImgSignature" runat="server" />

            </div>

        </div>
    
    </div>

</div>

</asp:Content>
