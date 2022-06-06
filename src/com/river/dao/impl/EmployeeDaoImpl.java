package com.river.dao.impl;


import com.river.dao.EmployeeDao;
import com.river.entity.Employee;
import com.river.util.DBUtil;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public List<Employee> findAll() {
        String sql = "select * from EMP";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, Employee.class);
    }

    @Override
    public Employee findById(int empno2) {
        String sql = "select * from EMP where empno = ?";
        Object[] params = {empno2};
        List<Employee> list = DBUtil.executeQuery(sql, params, Employee.class);

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Employee> findEmp(String cjob, int cdeptno) {
        String sql = "select * from EMP where job = ? and deptno = ?";
        Object[] parmas = {cjob, cdeptno};
        return DBUtil.executeQuery(sql, parmas, Employee.class);
    }

    @Override
    public int save(Employee emp) {
        String sql = "insert into EMP values(null, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {emp.getEname(), emp.getJob(), emp.getMgr(), new java.sql.Date(emp.getHireDate().getTime()),
                emp.getSal(), emp.getComm(), emp.getDeptno()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int update(Employee emp) {
        String sql = "update EMP set job = ?, sal = ?, deptno = ? where empno = ?";
        Object[] params = {emp.getJob(), emp.getSal(), emp.getDeptno(), emp.getEmpno()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int delete(int empno) {
        String sql = "delete from EMP where empno = ?";
        Object[] params = {empno};
        return DBUtil.executeUpdate(sql, params);
    }
}
