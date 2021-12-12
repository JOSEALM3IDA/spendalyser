package pt.josealm3ida.spendalyzer.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ExpenseRepository {

    public static final String PERSISTENCE_UNIT_EXPENSE = "expense_pu";

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public ExpenseRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_EXPENSE);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public Expense add(Expense expense) {
        entityManager.getTransaction().begin();
        entityManager.persist(expense);
        entityManager.getTransaction().commit();
        return expense;
    }

    public List<Expense> add(List<Expense> expenses) {
        entityManager.getTransaction().begin();
        for (Expense expense : expenses) {
            System.out.println(expense.toString());
            entityManager.persist(expense);
        }
        entityManager.getTransaction().commit();
        return expenses;
    }

    public List<Expense> findAll() {
        TypedQuery<Expense> query = entityManager.createNamedQuery(Expense.QUERY_FIND_ALL, Expense.class);
        return query.getResultList();
    }

    public Expense findById(Long id) {
        TypedQuery<Expense> query = entityManager.createNamedQuery(Expense.QUERY_FIND_BY_ID, Expense.class);
        query.setParameter(Expense.PARAM_ID, id);
        return query.getSingleResult();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
