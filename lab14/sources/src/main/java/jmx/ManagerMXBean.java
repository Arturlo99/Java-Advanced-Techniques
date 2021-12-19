package jmx;

import queue.system.CaseCategory;

import java.util.List;

public interface ManagerMXBean {

    public List<CaseCategory> getCategoryList();
    public void setCategoryList(List<CaseCategory> caseCategoryList);
}
