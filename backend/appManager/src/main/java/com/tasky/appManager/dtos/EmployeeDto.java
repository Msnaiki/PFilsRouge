package com.tasky.appManager.dtos;

import lombok.Data;

@Data
public class EmployeeDto {

        private Long employeeId;
        private String username;
        private ManagerDto managerDto;
        private String bestQuality;
}
