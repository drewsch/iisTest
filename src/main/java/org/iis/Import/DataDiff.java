package org.iis.Import;

import org.iis.DTO.EmployerValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataDiff {
    public static List<EmployerValueObject> getDiff(
            List<EmployerValueObject> firstList,
            List<EmployerValueObject> secondList,
            boolean checkDescription
    ) {
        if (secondList.isEmpty()) {
            return firstList;
        }

        List<EmployerValueObject> result = new ArrayList<>();

        for (EmployerValueObject item : firstList) {
            for (EmployerValueObject subItem : secondList) {
                if (item.equals(subItem)) {
                    if (!checkDescription) {
                        break;
                    }

                    if (Objects.equals(item.getDescription(), subItem.getDescription())) {
                        break;
                    } else {
                        item.setDescription(subItem.getDescription());
                    }
                }

                result.add(item);
                break;
            }
        }

        return result;
    }
}
