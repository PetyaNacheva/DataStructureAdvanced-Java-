import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class AgencyImpl implements Agency {
    private Map<String, Invoice> invoices;
    private Set<Invoice>payed;
    private Map<LocalDate, Map<String, Invoice>> dueDates;

    public AgencyImpl() {
    this.invoices=new HashMap<>();
    this.payed = new HashSet<>();
    this.dueDates = new HashMap<>();
    }

    @Override
    public void create(Invoice invoice) {
        if(this.contains(invoice.getNumber())){
            throw new IllegalArgumentException();
        }
        this.invoices.put(invoice.getNumber(),invoice);
        Map<String, Invoice> current = this.dueDates.get((invoice.getDueDate()));
        if(current==null){
            current = new HashMap<>();
        }
        current.put(invoice.getNumber(),invoice);
        this.dueDates.put(invoice.getDueDate(),current);
    }

    @Override
    public boolean contains(String number) {
       return this.invoices.containsKey(number);
    }

    @Override
    public int count() {
        return this.invoices.size();
    }

    @Override
    public void payInvoice(LocalDate dueDate) {
        if(!this.dueDates.containsKey(dueDate)){
            throw new IllegalArgumentException();
        }
        this.dueDates.get(dueDate);
        Map<String, Invoice>atDate = this.dueDates.get(dueDate);
        atDate.forEach((k,v)-> {
           v.setSubtotal(0);
           this.payed.add(v);
           this.invoices.put(k,v);
        });

        this.dueDates.put(dueDate,atDate);

    }

    @Override
    public void throwInvoice(String number) {
        Invoice invice = this.invoices.get(number);
        if(invice==null){
        throw new IllegalArgumentException();
    }
        this.dueDates.get(invice.getDueDate()).remove(invice.getNumber());
        this.invoices.remove((number));
    }

    @Override
    public void throwPayed() {
        this.payed.forEach(i->{
            this.dueDates.get(i.getDueDate()).remove(i.getNumber());
            this.invoices.remove(i.getNumber());
        });
        this.payed.clear();
    }

    @Override
    public Iterable<Invoice> getAllInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {
        return this.invoices
                .values()
                .stream()
                .filter(i->(i.getIssueDate().isAfter(startDate)||i.getIssueDate().isEqual(startDate))
                        &&(i.getIssueDate().isBefore(endDate)||i.getIssueDate().isEqual(endDate)))
                        .sorted(Comparator.comparing(Invoice::getIssueDate).thenComparing(Invoice::getDueDate))
                        .collect(Collectors.toUnmodifiableList());


                //sorted Реално се замества с l,r)-> {
        //                           int issueDateResult= l.getIssueDate().compareTo(r.getIssueDate());
        //                           return issueDateResult==0 ? l.getDueDate().compareTo(r.getDueDate()) : issueDateResult;
        //
        //                        }
    }

    @Override
    public Iterable<Invoice> searchByNumber(String number) {
        List<Invoice>result = invoices.values()
                .stream()
                .filter(i->i.getNumber().contains(number))
                .collect(Collectors.toUnmodifiableList());

        if(result.isEmpty()){
            throw new IllegalArgumentException();
        }
        return result;
    }

    @Override
    public Iterable<Invoice> throwInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {
        List<Invoice> result = this.invoices
                .values()
                .stream()
                .filter(i->(i.getDueDate().isAfter(startDate))
                        &&(i.getDueDate().isBefore(endDate)))
                .collect(Collectors.toUnmodifiableList());

        if(result.isEmpty()){
            throw new IllegalArgumentException();
        }
        result.forEach(i->{
            this.dueDates.get(i.getDueDate()).remove(i.getNumber());
            this.invoices.remove(i.getNumber());
        });
        return result;
    }

    @Override
    public Iterable<Invoice> getAllFromDepartment(Department department) {
        return this.invoices
                .values()
                .stream()
                .filter(i->i.getDepartment().equals(department))
                .sorted((f,s)->{
                  int subtotalResult =   Double.compare(s.getSubtotal(),f.getSubtotal());
                  return subtotalResult ==0 ? f.getIssueDate().compareTo(s.getIssueDate()) :subtotalResult;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Iterable<Invoice> getAllByCompany(String companyName) {
        return this.invoices
                .values()
                .stream()
                .filter(i->i.getCompanyName().equals(companyName))
                .sorted((l,r)->r.getNumber().compareTo(l.getNumber()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void extendDeadline(LocalDate endDate, int days) {
        if(!dueDates.containsKey(endDate)){
            throw new IllegalArgumentException();
        }

        this.dueDates.get(endDate).forEach((k,v)->v.getDueDate().plusDays(days));

    }
}
