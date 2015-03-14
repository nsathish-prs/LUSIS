<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeHomePage.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeHomePage" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
    <div class="col-lg-7" style="margin-top:2%">
            <div class="col-lg-4 col-md-6">
                <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">
                                        <asp:Label ID="lblNewReqCount" runat="server" Text="Count"></asp:Label></div>
                                    <div>New Requisitions!</div>
                                </div>
                            </div>
                        </div>
                            
                            <div class="panel-footer">
                                <span class="pull-left"><a href="storeNewRequisitions.aspx">View Details</a></span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                            
                    </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-shopping-cart fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"><asp:Label ID="lblPurReqCount" runat="server" Text="Count"></asp:Label></div>
                                <div>Items Require Purchase!</div>
                            </div>
                        </div>
                    </div>
                            
                        <div class="panel-footer">
                            <span class="pull-left"><a href="storePurchaseRequired.aspx">View Details</a></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                            
                </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-shopping-cart fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"><asp:Label ID="lblPendingDescCount" runat="server" Text="Count"></asp:Label></div>
                                <div>Pending Discrepancy!</div>
                            </div>
                        </div>
                    </div>
                            
                        <div class="panel-footer">
                            <span class="pull-left"><a href="storeVoucherList.aspx">View Details</a></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                            
                </div>
            </div>
        </div>                    
</div>

<div class="row">

<div class="col-lg-9">

<div class="col-lg-2">
</div>

</div>

</div>

</asp:Content>
