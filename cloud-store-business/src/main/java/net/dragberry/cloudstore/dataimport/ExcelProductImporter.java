package net.dragberry.cloudstore.dataimport;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.cloudstore.business.CategoryServiceLocal;
import net.dragberry.cloudstore.business.ProductServiceLocal;
import net.dragberry.cloudstore.domain.Category;
import net.dragberry.cloudstore.domain.Product;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

@Stateless
public class ExcelProductImporter implements ProductImporterLocal {

	@Inject
	private ProductServiceLocal productService;
	@Inject
	private CategoryServiceLocal categoryService;

	@Override
	public void doImport(InputStream is) throws Exception {
		WorkbookSettings settings = new WorkbookSettings();
		settings.setEncoding("CP1251");
		Workbook workbook = Workbook.getWorkbook(is);
		Sheet sheet = workbook.getSheet(0);
		for (int i = 1; i < sheet.getRows(); i++) {
			Product product = new Product();
			// product.setCountry(getString(sheet.getCell(0, i)));
			// product.setManufacturer(getString(sheet.getCell(1, i)));
			product.setTitle(getString(sheet.getCell(2, i)));
			Set<Category> categories = getCategorySet(sheet.getCell(3, i), sheet.getCell(4, i));
			// if (categories.isEmpty()) {
			// System.out.print("");
			// }
			product.setCategories(categories);
			product.setCost(getBigDecimal(sheet.getCell(5, i)));
			// product.setQuantity(getInteger(sheet.getCell(6, i)));
			// product.setColors(getString(sheet.getCell(7, i)));
			product.setDescription(getString(sheet.getCell(8, i)));
			product.setFullDescription(getString(sheet.getCell(9, i)));
			// product.setImgLink(getString(sheet.getCell(10, i)));
			// product.setKeywords(getString(sheet.getCell(11, i)));
			productService.createProduct(product);
		}
	}

	private Integer getInteger(Cell cell) {
		return StringUtils.isNumeric(getString(cell)) ? Integer.valueOf(getString(cell)) : 0;
	}

	private BigDecimal getBigDecimal(Cell cell) {
		if (StringUtils.isNotBlank(cell.getContents())) {
			return new BigDecimal(cell.getContents());
		}
		return null;
	}

	private Set<Category> getCategorySet(Cell cell, Cell cell2) {
		Set<Category> categorySet = new HashSet<Category>();
		String value = getString(cell);
		String[] values = value.split("/");
		for (String categoryValue : values) {
			Category category = categoryService.fetchCategoryByTitle(categoryValue);
			if (category != null) {
				categorySet.add(category);
			}
		}
		value = getString(cell2);
		values = value.split("/");
		for (String categoryValue : values) {
			Category category = categoryService.fetchCategoryByTitle(categoryValue);
			if (category != null) {
				categorySet.add(category);
			}
		}
		return categorySet;
	}

	private String getString(Cell cell) {
		return cell.getContents() == null ? StringUtils.EMPTY : cell.getContents();
	}

}
