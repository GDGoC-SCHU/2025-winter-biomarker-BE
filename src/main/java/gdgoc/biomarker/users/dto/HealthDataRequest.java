package gdgoc.biomarker.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthDataRequest {
    private double height;
    private double weight;
    private double body_fat_percentage; // 체지방률
    private double skeletal_muscle_mass; // 골격근량
    private double bmr; // 기초대사량
    private double body_fat_mass; //체지방량
    private String goal; // 목적
}
