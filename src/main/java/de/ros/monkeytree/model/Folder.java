package de.ros.monkeytree.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Folder model.
 */
@Getter
@Setter
public class Folder {
    private String name;
    private int level;
    private List<Folder> subfolders = new ArrayList<>();

    public void addFolder(Folder subFolder) {
        if (Objects.nonNull(subFolder)) {
            subfolders.add(subFolder);
        }
    }

}
