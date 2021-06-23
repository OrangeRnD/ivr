<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<div class="tblBox">
						<form data-type="search" name="search_popup_frm" data-btn-search="btn_popup_search">
						<input type="hidden" name="pageNo" value="1"/>
						<table class="tbltype01">
							<colgroup><col width="11%"><col width="*"><col width="11%"></colgroup>
							<tr id="first">
								<th>서비스코드</th>
								<td colspan="2">
									<input type="text"  class="input01 wdt200px" maxlength="10" name="param[SRVC_CD]">
								</td>
							</tr>
							<tr>
								<th>서비스명</th>
								<td>
									<input type="text"  class="input01 wdt200px" maxlength="50" name="param[SRVC_NM]">
								</td>
								<td>
									<div class="btnRight">
										<a class="btntype02 btn_primary" id="btn_popup_search">조회</a>
									</div>
								</td>
							</tr>
						</table>
						</form>
					</div>
					<div id="srvc_popup_list">
						<jsp:include page="./srvccd_search_list.jsp"></jsp:include>
					</div>