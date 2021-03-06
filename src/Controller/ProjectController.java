/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Project;
import Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JB
 */
public class ProjectController {
    public void save(Project project) {
        String sql = "INSERT INTO projects "
                + "(name,"
                + "description,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?)";
                
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            statement.execute();
        } catch (Exception error) {
            throw new RuntimeException("Erro ao salvar o projeto no banco de dados", error);           
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (Exception error) {
            throw new RuntimeException("Erro ao atualizar o projeto no banco de dados", error);           
        } finally { 
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int idProject) {
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, idProject);
            statement.execute();
        } catch (Exception error) {
            throw new RuntimeException("Erro ao deletar o projeto no banco de dados", error);           
        } finally { 
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
               
        List<Project> projects = new ArrayList<Project>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);               
            }
        } catch (Exception error) {
            throw new RuntimeException("Erro ao buscar os projetos no banco de dados", error);           
        } finally { 
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        return projects;
    }
}
