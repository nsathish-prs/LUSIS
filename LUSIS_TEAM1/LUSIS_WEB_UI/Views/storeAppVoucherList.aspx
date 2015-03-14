<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeAppVoucherList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeAppVoucherList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header"> Approve Discrepancy Voucher List  </h1>

        <div class="col-lg-8">

            <div class="table-responsive">

                <asp:Table ID="TableAppVouList" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Voucher ID</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Date</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Raised By</asp:TableHeaderCell>
                        <%--<asp:TableHeaderCell>Status</asp:TableHeaderCell>--%>
                    </asp:TableHeaderRow>
               
                    <asp:TableRow>
                                        
                    </asp:TableRow>

                </asp:Table>
        
            </div>

        </div>
    
    </div>

</div>

</asp:Content>
