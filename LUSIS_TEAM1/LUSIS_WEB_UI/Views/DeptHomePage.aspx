<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptHomePage.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptHomePage" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
                    <div class="col-lg-8">

    <div class="col-lg-12" style="margin-top:5%">
                    
    
<div class="col-md-4">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-tasks fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><asp:Label ID="LblCont1" runat="server" Text="Count"></asp:Label></div>
                                        <div><asp:Label ID="LblCont1Text" runat="server" Text="Disbursement List is Ready"></asp:Label></div>
                                    </div>
                                </div>
                            </div>
                            
                                <div class="panel-footer">
                                    <span class="pull-left"><%--<a href="DeptDisbursementLists.aspx">--%>
                                        <asp:LinkButton ID="LinkCont1" runat="server">View Details</asp:LinkButton><%--</a>--%></span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-comments fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge">
                                             <asp:Label ID="LblCont2" runat="server" Text="Count"></asp:Label></div>
                                        <div><asp:Label ID="LblCont2Text" runat="server" Text="Pending Requisitions"></asp:Label></div>
                                    </div>
                                </div>
                            </div>
                            
                                <div class="panel-footer">
                                    <span class="pull-left"><%--<a href="DeptAppRequisitionList.aspx">--%>
                                        <asp:LinkButton ID="LinkCont2" runat="server">View Details</asp:LinkButton><%--</a>--%></span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            
                        </div>
                    </div>

                    
                    
                </div>
                <!-- /.row -->

            
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>




                   <%-- <div class="col-lg-3 col-md-6">
                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-shopping-cart fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><asp:Label ID="Label3" runat="server" Text="Count"></asp:Label></div>
                                        <div>New Orders!</div>
                                    </div>
                                </div>
                            </div>
                            
                                <div class="panel-footer">
                                    <span class="pull-left"><a href="#">View Details</a></span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            
                        </div>
                    </div>--%>
                 
                    </div>                    
                </div> 
</asp:Content>
