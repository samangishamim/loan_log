package base.repository;

import base.entity.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl <T extends BaseEntity<ID>
        , ID extends Serializable> implements BaseRepository<T, ID> {

    public SessionFactory sessionFactory;


    public BaseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T saveOrUpdate(T entity) {
        Session session = sessionFactory.getCurrentSession();
        if (entity.getId() == null)
            session.persist(entity);
        else
            entity = session.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        Session session = sessionFactory.getCurrentSession();
        T t = session.get(getEntityClass(), id);
        return Optional.ofNullable(t);
    }

    public abstract Class<T> getEntityClass();

    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<T> query = session.createQuery(String.format("FROM %s", getMyClass()), getEntityClass());
            List<T> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    public abstract String getMyClass();
}
