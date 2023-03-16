package polyakova.tests.statistic;

import io.qameta.allure.internal.Allure2ModelJackson;
import io.qameta.allure.internal.shadowed.jackson.databind.MapperFeature;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Generate report
 * use allure-results
 *
 * @author Iuliia Poliakova
 */
public class StepStatisticRun {
    public static void main(String[] args) throws IOException {
        Map<String, RowModel> stepStat = new TreeMap<>();

        final File inDir = Paths.get("tests", "target", "allure-results").toFile();
        System.out.println("In Dir: " + inDir.getAbsolutePath());
        findJson(inDir, stepStat);

        final File outFile = File.createTempFile("allure-stat", ".xlsx");
        writeXlsx(outFile, stepStat);

        System.out.println("Report: " + outFile.getAbsolutePath());
    }

    public static void findJson(File dir, Map<String, RowModel> stepStat) {
        final File[] files = dir.listFiles((FilenameFilter) new SuffixFileFilter("-result.json"));
        for (File file : files) {
            try {
//                System.out.println(file.getAbsolutePath());
                readJson(file, stepStat);
            } catch (Exception e) {
                System.out.println("Error " + file.getAbsolutePath());
            }
        }
    }

    private static void writeXlsx(File file, Map<String, RowModel> stepStat) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(" Allure step duration ");
            int rownum = 0;
            Row row = sheet.createRow(rownum++);
            int cellid = 0;
            Cell cell = row.createCell(cellid);
            cell.setCellValue("Step");
            sheet.setColumnWidth(cellid++, 25 * 800);
            cell = row.createCell(cellid);
            cell.setCellValue("passed");
            sheet.setColumnWidth(cellid++, 25 * 70);
            cell = row.createCell(cellid);
            cell.setCellValue("duration");
            sheet.setColumnWidth(cellid++, 25 * 90);
            cell = row.createCell(cellid);
            cell.setCellValue("avr");
            sheet.setColumnWidth(cellid++, 25 * 80);
            cell = row.createCell(cellid);
            cell.setCellValue("error");
            sheet.setColumnWidth(cellid++, 25 * 70);
            cell = row.createCell(cellid);
            cell.setCellValue("duration");
            sheet.setColumnWidth(cellid++, 25 * 90);
            cell = row.createCell(cellid);
            cell.setCellValue("avr");
            sheet.setColumnWidth(cellid++, 25 * 80);

            for (RowModel rowModel : stepStat.values()) {
                row = sheet.createRow(rownum++);
                cellid = 0;
                cell = row.createCell(cellid++);
                cell.setCellValue(rowModel.getStepName());
                cell = row.createCell(cellid++);
                cell.setCellValue(rowModel.getCountPassed());
                cell = row.createCell(cellid++);
                cell.setCellValue(rowModel.getDurationPassed());
                cell = row.createCell(cellid++);
                if (rowModel.getCountPassed() != 0) {
                    cell.setCellFormula("C" + rownum + "/B" + rownum);
                }
                cell = row.createCell(cellid++);
                cell.setCellValue(rowModel.getCountError());
                cell = row.createCell(cellid++);
                cell.setCellValue(rowModel.getDurationError());
                cell = row.createCell(cellid++);
                if (rowModel.getCountError() != 0) {
                    cell.setCellFormula("F" + rownum + "/E" + rownum);
                }
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
        }
    }

    public static void readJson(File file, Map<String, RowModel> stepStat) throws IOException {
        final ObjectMapper mapper = Allure2ModelJackson.createMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TestResult testResult = mapper.readValue(file, TestResult.class);

//        System.out.println(testResult.getTestCaseName());
        for (StepResult step : testResult.getSteps()) {
            solveStep(step, stepStat);
        }
    }

    private static void solveStep(StepResult step, Map<String, RowModel> stepStat) {
        RowModel rowModel = stepStat.get(step.getName());
        if (rowModel == null) {
            if (step.getStatus() == Status.PASSED) {
                rowModel = new RowModel(step.getName(),
                        1,
                        step.getStop() - step.getStart(),
                        0,
                        0);
            } else {
                rowModel = new RowModel(step.getName(),
                        0,
                        0,
                        1,
                        step.getStop() - step.getStart());
            }
            stepStat.put(step.getName(), rowModel);
        } else {
            if (step.getStatus() == Status.PASSED) {
                rowModel.setCountPassed(rowModel.getCountPassed() + 1);
                rowModel.setDurationPassed(rowModel.getDurationPassed() + step.getStop() - step.getStart());
            } else {
                rowModel.setCountError(rowModel.getCountError() + 1);
                rowModel.setDurationError(rowModel.getDurationError() + step.getStop() - step.getStart());
            }
        }
        for (StepResult childStep : step.getSteps()) {
            solveStep(childStep, stepStat);
        }
    }
}
