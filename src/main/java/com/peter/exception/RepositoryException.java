/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.exception;

/**
 *
 * @author peter
 */
public class RepositoryException extends Exception {

    /**
     * Creates a new instance of <code>RepositoryException</code> without detail
     * message.
     */
    public RepositoryException() {
        //The empty constructor is needed by the Spring framework to generate the entities
    }

    /**
     * Constructs an instance of <code>RepositoryException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RepositoryException(String msg) {
        super(msg);
    }
}
