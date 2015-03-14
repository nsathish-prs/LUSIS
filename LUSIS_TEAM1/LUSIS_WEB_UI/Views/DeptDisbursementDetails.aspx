<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptDisbursementDetails.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptDisbursementDetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

 <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Disbursement Details
                        </h1>
                       
                        <div class="col-lg-3" style="margin-bottom: 1%; color:white">
                               Disbursement ID :  
                               <asp:Label ID="DisbursementID" runat="server" Text="Label_DisbId"></asp:Label>
                               <br />
                               Department :  <asp:Label ID="deptLabel" runat="server" Text="Label"></asp:Label><br />
                               Representative Name :  <asp:Label ID="RepName" runat="server" Text="Label"></asp:Label><br />
                               Collection Point :  <asp:Label ID="CollectPoint" runat="server" Text="Label"></asp:Label><br />
                               </div>
                               <div class="col-lg-4"><asp:Button ID="ButtonCancel" class = "btn btn-primary" runat="server" 
                                       Text="Cancel Disbursement"  onclick="ButtonCancel_Click" 
                                       Visible="False" /></div>
                               <div style="clear:both"></div>
                               <div class="col-lg-8">
                                 
                                 

                                   <asp:GridView ID="GridView1" 
                                       class="table table-bordered table-hover table-striped" runat="server" 
                                       AutoGenerateColumns="False">
                                       <Columns>
                                           <asp:BoundField HeaderText="Item Category" 
                                               DataField="RetrievalByItem.Item.Category" />
                                           <asp:BoundField HeaderText="Item Description" 
                                               DataField="RetrievalByItem.Item.Description" />
                                           <asp:BoundField HeaderText="Unit" 
                                               DataField="RetrievalByItem.Item.UnitofMeasure" />
                                           <asp:BoundField HeaderText="Quantity" DataField="RetrievedQuantity" />
                                           <asp:BoundField DataField="Status" HeaderText="Status" />
                                       </Columns>
                                   </asp:GridView>

                                   
                                   <br />
                                   <asp:Label ID="Label1" runat="server" Text="Received By" Visible="False"></asp:Label>
                                   <br />

                                   <asp:Label ID="SignatureLabel" runat="server" Text="Label" Visible="False"></asp:Label>
                                   <br />
                                   
                                   <asp:Image ID="ImageSignature" runat="server" Width="124px" Height="43px" 
                                       Visible="False" />

                              </div>
                               <div style="clear:both"></div>
                          <div class="table-responsive">
                             
                             </div>  
                               
                    </div> 
                    </div>
                  
                  </asp:Content>

