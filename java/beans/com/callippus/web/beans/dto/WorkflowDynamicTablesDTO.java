package com.callippus.web.beans.dto;

public class WorkflowDynamicTablesDTO extends CommonDTO {
	private String tableDTO;
	private String tableName;

	public String getTableDTO() {
		return tableDTO;
	}

	public void setTableDTO(String tableDTO) {
		this.tableDTO = tableDTO;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
