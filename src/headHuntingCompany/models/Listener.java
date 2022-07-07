package headHuntingCompany.models;

import headHuntingCompany.models.JobPost;

public interface Listener<T> {
    boolean onNotification(T item, JobPost jobPost);
}
