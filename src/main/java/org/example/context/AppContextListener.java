package org.example.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.example.JdbcConnection;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.GenreRepository;
import org.example.repository.impl.AuthorRepositoryImpl;
import org.example.repository.impl.BookRepositoryImpl;
import org.example.repository.impl.GenreRepositoryImpl;
import org.example.service.AuthorService;
import org.example.service.BookService;
import org.example.service.GenreService;
import org.example.service.impl.AuthorServiceImpl;
import org.example.service.impl.BookServiceImpl;
import org.example.service.impl.GenreServiceImpl;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        BookRepository bookRepository = new BookRepositoryImpl(JdbcConnection.getConnection());
        BookService bookService = new BookServiceImpl(bookRepository);

        sce.getServletContext().setAttribute("bookService", bookService);

        AuthorRepository authorRepository = new AuthorRepositoryImpl(JdbcConnection.getConnection());
        AuthorService authorService = new AuthorServiceImpl(authorRepository);

        sce.getServletContext().setAttribute("authorService", authorService);

        GenreRepository genreRepository = new GenreRepositoryImpl(JdbcConnection.getConnection());
        GenreService genreService = new GenreServiceImpl(genreRepository);

        sce.getServletContext().setAttribute("genreService", genreService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
    }
}
