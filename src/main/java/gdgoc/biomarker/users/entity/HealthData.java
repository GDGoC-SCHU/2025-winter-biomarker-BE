package gdgoc.biomarker.users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "healthdata")
public class HealthData {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    private double height; // 키 (cm)
    private double weight; // 체중 (kg)
    private double body_fat_percentage; // 체지방률 (%)
    private double skeletal_muscle_mass; // 골격근량 (kg)
    private double bmr; // 기초대사량 (kcal)
    private double body_fat_mass; // 체지방량 (kg)
    private String goal; // 식단목표

    private double bmi; // 계산된 BMI 값

    private String gender; // 성별

    public HealthData(double height, double weight, double body_fat_percentage, double skeletal_muscle_mass, double bmr, double body_fat_mass, String goal,String gneder) {
        this.height = height;
        this.weight = weight;
        this.body_fat_percentage = body_fat_percentage;
        this.skeletal_muscle_mass = skeletal_muscle_mass;
        this.bmr = bmr;
        this.body_fat_mass = body_fat_mass;
        this.goal = goal;
        this.bmi = calculateBMI(height,weight);
        this.gender = gneder;
    }

    // BMI 계산 함수
    public double calculateBMI(double height, double weight) {
        double heightInMeters = height / 100;
        return weight / (heightInMeters * heightInMeters);
    }
}
