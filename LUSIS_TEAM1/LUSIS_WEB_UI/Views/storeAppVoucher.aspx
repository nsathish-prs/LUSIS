<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeAppVoucher.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeAppVoucher" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header"> Approve Discrepancy Voucher</h1>

        <div class="col-lg-8" style="margin-bottom:1%; color: white; line-height: 2em">
            <div>Voucher No: <asp:Label ID="LblVoucherNo" runat="server" Text="Label"></asp:Label></div>
            <div>Date: <asp:Label ID="LblDate" runat="server" Text="Label"></asp:Label></div>
            <div>Raised By: <asp:Label ID="LblRaisedBy" runat="server" Text="Label"></asp:Label></div>
            <br />
        </div>

        <div style="clear:both"></div>

        <div class="col-lg-8">

            <div class="table-responsive">

                <asp:Table ID="TableVouItems" 
                    CssClass="table table-bordered table-hover table-striped" runat="server">
               
                    <asp:TableHeaderRow>
                        <asp:TableHeaderCell>Item No </asp:TableHeaderCell>
                        <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Qty. Adjusted</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Reason</asp:TableHeaderCell>
                        <asp:TableHeaderCell>Approval</asp:TableHeaderCell>
                    </asp:TableHeaderRow>

                </asp:Table>
        
            </div>

            <div class="col-lg-12" style="margin-top: 1.5%">
                    
                    <div style="clear:both"></div>
                    <div>
                            <div class="col-lg-1" style="width:auto"><asp:Button ID="BtnApprove" runat="server" Text="Approve" 
                                class="btn btn-primary" type="submit" onclick="BtnApprove_Click" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            
                            <div class="col-lg-1" style="width:auto"><asp:Button ID="BtnReject" runat="server" Text="Reject" class="btn btn-primary" 
                                type="submit" onclick="BtnReject_Click" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            
                            <div class="col-lg-1" style="width:auto"><asp:Button ID="BtnBack" runat="server" Text="Back" class="btn btn-primary" 
                                type="submit" onclick="BtnBack_Click" /></div>
                    </div>

            </div>

            <div style="clear:both"></div>

            <div class="col-lg-6" style="margin-top: 1.5%">
                    
                    <asp:Image ID="ImgSignature" runat="server" />

            </div>

        </div>
    
    </div>

</div>

</asp:Content>
