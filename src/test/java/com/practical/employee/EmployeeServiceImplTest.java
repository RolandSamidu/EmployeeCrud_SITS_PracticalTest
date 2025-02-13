package com.practical.employee;

import com.practical.employee.exeptionHandle.EmployeeNotFoundException;
import com.practical.employee.model.Employee;
import com.practical.employee.repository.EmployeeRepository;
import com.practical.employee.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Roland");
        employee.setLastName("Appuhamy");
        employee.setEmail("rolandappuhamy@gmail.com");
        employee.setSalary(20000.00);
    }

    @Test
    void createEmployee_ShouldSaveAndReturnEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getSalary(), result.getSalary());

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee_WhenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals(employee.getFirstName(), result.get().getFirstName());
        assertEquals(employee.getLastName(), result.get().getLastName());
        assertEquals(employee.getEmail(), result.get().getEmail());
        assertEquals(employee.getSalary(), result.get().getSalary());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_ShouldReturnEmpty_WhenNotExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertFalse(result.isPresent());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setLastName("Fernando");
        updatedEmployee.setEmail("dewanthafdo@gmail.com");

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(updatedEmployee.getLastName(), result.getLastName());
        assertEquals(updatedEmployee.getEmail(), result.getEmail());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(updatedEmployee);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee updatedEmployee = new Employee();
        updatedEmployee.setLastName("Fernando");
        updatedEmployee.setEmail("dewanthafdo@gmail.com");

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updatedEmployee);
        });

        assertEquals("Employee with ID 1 not found", exception.getMessage());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenExists() {
        doNothing().when(employeeRepository).deleteById(1L);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
