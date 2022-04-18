package com.together.levelup.domain.category;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryChannel> categoryChannels = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> childs = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.childs.add(child);
        child.setParent(this);
    }

    public void addCategoryChannel(CategoryChannel categoryChannel) {
        this.categoryChannels.add(categoryChannel);
        categoryChannel.setCategory(this);
    }

    public static Category createCategory(String name, CategoryChannel ...categoryChannels) {
        Category category = new Category();

        category.setName(name);

        //연관관계 작업
        for (CategoryChannel categoryChannel : categoryChannels) {
            category.addCategoryChannel(categoryChannel);
        }
        return category;
    }

}
